package com.github.jts.timer;

import com.github.jts.tasks.Task;
import com.github.utilities.validators.Preconditions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public sealed class Timer permits StaticTimer {

    public static final Logger LOGGER = Logger.getLogger(Timer.class.getName());

    private final TimerConfig config;
    private final List<TimerTask> tasks = new CopyOnWriteArrayList<>();

    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor(r ->
                    Thread.ofPlatform().name("timer-execution-thread")
                            .uncaughtExceptionHandler((thread, exception) -> LOGGER.log(Level.SEVERE, "Uncaught exception in timer execution thread", exception))
                            .unstarted(r));

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);
    private final AtomicBoolean paused = new AtomicBoolean(false);

    private volatile ScheduledFuture<?> scheduledCycle;

    public Timer(TimerConfig config) {
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

    public void newTask(Predicate<? extends Task> canRun, Task task) {
        if (isFull())
            throw new IndexOutOfBoundsException("The maximum number of tasks has been reached!");

        tasks.add(new TimerTask(Preconditions.parameterNotNull(canRun, "canRun"),
                Preconditions.parameterNotNull(task, "task")));

        if (config.START_ON_CREATE.get() && !isRunning() && !isStopped())
            start();
    }

    public boolean remTask(Task task) {
        for (TimerTask timerTask : tasks) {
            if (timerTask.task().equals(task)) {
                tasks.remove(timerTask);
                return true;
            }
        }
        return false;
    }

    private boolean isStopped() {
        return stopped.get();
    }

    private boolean isPaused() {
        return paused.get();
    }

    private boolean isRunning() {
        return started.get() && !stopped.get() && !paused.get();
    }

    private long getIntervalNanos() {
        return config.CYCLE_INTERVAL.get().toNano();
    }

    public void start() {
        if (isStopped())
            throw new IllegalStateException("This timer has already been shut down and cannot be restarted.");

        if (!started.compareAndSet(false, true))
            return;

        scheduledCycle = executor.scheduleWithFixedDelay(this::runCycle, 0, getIntervalNanos(), TimeUnit.NANOSECONDS);
    }

    private void runCycle() {
        if (isStopped() || isPaused()) return;

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

    public void shutdownNow() {
        stopped.set(true);
        if (scheduledCycle != null)
            scheduledCycle.cancel(true);
        executor.shutdown();
    }

    public void shutdown() {
        stopped.set(true);
        if (scheduledCycle != null)
            scheduledCycle.cancel(false);
        executor.shutdown();
    }

    //public void pause() {
    //    paused.set(true);
    //    if (scheduledCycle != null)
    //        scheduledCycle.cancel(false);
    //}
//
    //public void resume() {
    //    if (isStopped()) return;
    //    paused.set(false);
    //    scheduledCycle = executor.scheduleWithFixedDelay(this::runCycle, 0, getIntervalNanos(), TimeUnit.NANOSECONDS);
    //}
}






















