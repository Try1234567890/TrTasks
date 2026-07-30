package com.github.jts.timer;

import com.github.jts.tasks.Task;
import com.github.utilities.validators.Preconditions;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimerImpl implements Timer {
    private static final Object RUNNING_LOCK = new Object();
    public static final Logger LOGGER = Logger.getLogger(TimerImpl.class.getName());

    private final TimerConfig config;
    private final CopyOnWriteArraySet<TimerTask> tasks = new CopyOnWriteArraySet<>();
    private final CopyOnWriteArraySet<TimerTask> paused = new CopyOnWriteArraySet<>();
    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor(r ->
                    Thread.ofPlatform().name("timer-execution-thread")
                            .uncaughtExceptionHandler((thread, exception) -> LOGGER.log(Level.SEVERE, "Uncaught exception in timer execution thread", exception))
                            .unstarted(r));

    private final AtomicReference<TimerState> state = new AtomicReference<>(TimerState.CREATED);
    private volatile ScheduledFuture<?> scheduledCycle;

    public TimerImpl(TimerConfig config) {
        this.config = Preconditions.simpleNotNull(config, new TimerConfig());
        if (this.config.START_ON_CREATE.get())
            start();
    }

    public TimerConfig config() {
        return config;
    }

    public boolean isFull() {
        int maxSize = config.MAX_TASKS.get();
        return maxSize >= 0 && tasks.size() >= maxSize;
    }

    @Override
    public void pause(Task task) {
        unregister(task).ifPresent(paused::add);
    }

    public final void register(Predicate<? extends Task> canRun, Task task) {
        if (isFull()) {
            throw new IndexOutOfBoundsException("The maximum number of tasks has been reached!");
        }
        if (isStopping()) {
            LOGGER.log(Level.WARNING, "", new IllegalStateException("This timer is currently shutting down or has already shut down and new task cannot be registered."));
            return;
        }

        synchronized (RUNNING_LOCK) {
            tasks.add(new TimerTask(Preconditions.parameterNotNull(canRun, "canRun"),
                    Preconditions.parameterNotNull(task, "task")));
        }

        if (config.START_ON_CREATE.get()
                && !isStarted() && !isStopped()) {
            start();
        }
    }

    public final Optional<TimerTask> unregister(Task task) {
        return safeRemove(task, tasks);
    }

    @Override
    public Optional<TimerTask> unpause(Task task) {
        return safeRemove(task, paused);
    }

    private <T extends Task> Optional<T> safeRemove(Task task, Collection<T> tasks) {
        for (T currTask : tasks) {
            if (currTask.equals(task)) {
                synchronized (RUNNING_LOCK) {
                    tasks.remove(currTask);
                    return Optional.of(currTask);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public final TimerState getState() {
        return state.get();
    }

    private long getIntervalNanos() {
        return config.CYCLE_INTERVAL.get().toNano();
    }

    public void start() {
        if (isStopping()) {
            throw new IllegalStateException("This timer is currently shutting down or has already shut down and cannot be restarted.");
        }

        if (isStarted()) {
            LOGGER.warning("Cannot start the timer when it's already started!");
            return;
        }


        scheduledCycle = executor.scheduleWithFixedDelay(this::runCycle, 0, getIntervalNanos(), TimeUnit.NANOSECONDS);
        changeState(TimerState.RUNNING);
    }

    private void runCycle() {
        if (isStopped() || isPaused()) return;
        changeState(TimerState.RUNNING);

        synchronized (RUNNING_LOCK) {
            for (TimerTask timerTask : tasks) {
                Task task = timerTask.task();
                try {
                    if (timerTask.test(task)) {
                        LOGGER.info("Running " + task);
                        task.run();
                    }
                } catch (Exception e) { // We do not catch errors, only exceptions.
                    LOGGER.log(Level.SEVERE, "An unexpected exception is thrown while evaluating " + task, e);
                }
            }
        }

        changeState(TimerState.STARTED);
    }

    public void shutdown() {
        if (isStopped() || !isStarted()) {
            LOGGER.warning("Cannot shutdown the timer if it's stopped or not started yet.");
            return;
        }

        changeState(TimerState.STOPPING);

        if (scheduledCycle != null)
            scheduledCycle.cancel(false);
        try {
            if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdown();
                changeState(TimerState.STOPPED);
            } else {
                LOGGER.severe("Timer shutting down process timed out (30 seconds). Shutting down now.");
                shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("An error occurs while shutting down the timer due to thread interruption.", e);
        }
    }

    public void shutdownNow() {
        if (isStopped() || !isStarted()) {
            LOGGER.warning("Cannot shutdown the timer if it's stopped or not started yet.");
            return;
        }

        if (scheduledCycle != null)
            scheduledCycle.cancel(true);
        executor.shutdown();

        changeState(TimerState.STOPPED);
    }

    public void pause() {
        if (isStopped() || !isStarted()) {
            LOGGER.warning("Cannot pause the timer if it's stopped or not started yet.");
            return;
        }
        changeState(TimerState.PAUSED);

        if (scheduledCycle != null)
            scheduledCycle.cancel(false);
    }

    public void resume() {
        if (isStopped() || !isStarted()) {
            LOGGER.warning("Cannot resume the timer if it's stopped or not started yet.");
            return;
        }

        changeState(TimerState.STARTED);
        scheduledCycle = executor.scheduleWithFixedDelay(this::runCycle, 0, getIntervalNanos(), TimeUnit.NANOSECONDS);
    }

    private void changeState(TimerState newState) {
        if (state.get() == newState) return;
        state.set(newState);
    }
}




