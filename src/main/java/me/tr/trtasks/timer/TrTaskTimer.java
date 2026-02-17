package me.tr.trtasks.timer;

import me.tr.trtasks.executor.TrTaskExecutor;
import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.time.TrTime;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrTaskTimer implements TrTimer {
    private long currentTime = 0L;
    private final TrTimerOptions options;
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final Queue<TrTask> queue = new PriorityQueue<>(Comparator.comparingLong(TrTask::nextRun));
    private final TrTaskExecutor executor = new TrTaskExecutor();

    public TrTaskTimer(TrTimerOptions options) {
        this.options = options == null ? new TrTimerOptions(this) : options;
    }

    public TrTaskTimer() {
        this(null);
    }

    public void register(TrTask task) {
        if (task == null)
            return;
        queue.add(task);
        if (!started.get()) {
            start();
        }
    }

    public TrTaskExecutor getExecutor() {
        return executor;
    }

    /**
     * Start the timer cycle.
     * <p>
     * <b>This method is called automatically when the first task
     * is registered.</b>
     *
     * @throws IllegalArgumentException if one between {@link TrTimerOptions#getDelay()} and {@link TrTimerOptions#getPeriod()} is negative or null;
     */
    public void start() {
        started.set(true);
        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        while (isReady()) {
                            TrTask task = queue.poll();

                            if (task == null) continue;

                            try {
                                executor.execute(task);
                                handle(task);
                            } catch (Throwable t) {
                                handleTaskException(new RuntimeException("The task " + task.id() + " threw an exception", t));
                            }
                        }

                        currentTime++;
                    }
                }, getDelay(), getPeriod()
        );
    }

    /**
     * Helper method to handle an exception thrown during a task execution.
     *
     * @param ex The task exception.
     * @implNote This method can be overridden for a custom handling of task exceptions.
     */
    protected void handleTaskException(RuntimeException ex) {
        ex.printStackTrace(System.err);
    }


    @Override
    public boolean isStarted() {
        return started.get();
    }

    @Override
    public TrTimerOptions getOptions() {
        return options;
    }

    private long getDelay() {
        TrTime delay = getOptions().getDelay();

        if (delay == null)
            throw new IllegalArgumentException("The delay is null");

        long delayMs = delay.toMillis();

        if (delayMs < 0)
            throw new IllegalArgumentException("The delay is negative.");


        return delayMs;
    }


    private long getPeriod() {
        TrTime period = getOptions().getPeriod();

        if (period == null)
            throw new IllegalArgumentException("The period is null");

        long periodMs = period.toMillis();

        if (periodMs < 0)
            throw new IllegalArgumentException("The period is negative.");


        return periodMs;
    }

    /**
     * Checks if the next task is ready to be executed.
     *
     * @return {@code true} if it is, otherwise {@code false}
     */
    private boolean isReady() {
        TrTask task = queue.peek();

        if (task == null
                || task.period().isNull()) {
            queue.poll();
            return false;
        }

        if (task.isPaused()) return false;

        return task.nextRun() <= currentTime;
    }

    /**
     * Handle a task after its execution.
     *
     * @param task The task to handle.
     */
    private void handle(TrTask task) {
        if (task.hasReachedEnd())
            return;

        task.setNextRun(currentTime);
        queue.add(task);
    }

}
