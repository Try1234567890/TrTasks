package me.tr.trtasks.tasks;

import me.tr.trtasks.builder.TrTaskBuilder;
import me.tr.trtasks.scheduler.TrStaticTaskScheduler;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.timer.TrTaskTimer;

import java.util.UUID;

public abstract class TrTask {
    private final String id;
    private final TrTime period;
    private final TrTime delay;
    private final boolean async;
    private TrTaskOptions options;
    private boolean cancelled; // The task is removed from scheduler.
    private boolean paused; // The task is ignored until un-paused.

    // internals
    private int runs;
    private long nextRunMs; // in ms

    public TrTask(String id, TrTime period, TrTime delay, boolean async, TrTaskOptions options) {
        this.id = id;
        this.period = period;
        this.delay = delay;
        this.async = async;
        this.options = options;
        setNextRun(delay.toMillis());
    }

    public TrTask(TrTaskOptions options, boolean async) {
        this(UUID.randomUUID().toString(), TrTime.EMPTY, TrTime.EMPTY, async, options);
    }

    public TrTask(TrTaskOptions options) {
        this(UUID.randomUUID().toString(), TrTime.EMPTY, TrTime.EMPTY, false, options);
    }

    public TrTask(String id, TrTime period, TrTime delay, boolean async) {
        this(id, period, delay, async, null);
    }

    public TrTask(String id, TrTime period, TrTime delay) {
        this(id, period, delay, false, null);
    }

    public TrTask(TrTime period, TrTime delay, boolean async) {
        this(UUID.randomUUID().toString(), period, delay, async, null);
    }

    public TrTask(TrTime period, TrTime delay) {
        this(UUID.randomUUID().toString(), period, delay, false, null);
    }

    public TrTask(String id, TrTime period, boolean async) {
        this(id, period, TrTime.EMPTY, async);
    }

    public TrTask(String id, TrTime period) {
        this(id, period, TrTime.EMPTY, false);
    }

    public TrTask(TrTime period, boolean async) {
        this(UUID.randomUUID().toString(), period, TrTime.EMPTY, async);
    }

    public TrTask(TrTime period) {
        this(UUID.randomUUID().toString(), period, TrTime.EMPTY, false);
    }

    public TrTask(String id, boolean async) {
        this(id, TrTime.EMPTY, async);
    }

    public TrTask(String id) {
        this(id, TrTime.EMPTY, false);
    }

    public TrTask(boolean async) {
        this(UUID.randomUUID().toString(), async);
    }

    public TrTask() {
        this(UUID.randomUUID().toString(), false);
    }

    public TrTask register() {
        TrStaticTaskScheduler.task(this);
        return this;
    }

    public TrTask register(TrTaskTimer timer) {
        timer.register(this);
        return this;
    }

    public String id() {
        return id;
    }

    public void pause() {
        this.paused = true;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public TrTime period() {
        return period;
    }

    public TrTime delay() {
        return delay;
    }

    public boolean isAsync() {
        return async;
    }

    public boolean isSync() {
        return !async;
    }

    public long nextRun() {
        return nextRunMs;
    }

    public void setNextRun(long currentTime) {
        this.nextRunMs = currentTime + period.toMillis();
    }

    protected <T extends TrTaskOptions> T setOptions(T options) {
        this.options = options;
        return options;
    }

    public TrTaskOptions getOptions() {
        if (options == null) {
            options = new TrTaskOptions(this);
        }
        return options;
    }

    public boolean hasReachedMaxRuns() {
        int maxRuns = getOptions().getMaxRun();
        return maxRuns > 0 && maxRuns <= runs;
    }

    public boolean hasReachedEnd() {
        if (period().isEmpty() && runs == 1)
            return true;

        if (hasReachedMaxRuns())
            return true;

        return isCancelled();
    }

    public void run() {
        this.runs++;
        task();
    }

    protected abstract void task();

    public static TrTaskBuilder builder() {
        return new TrTaskBuilder();
    }
}
