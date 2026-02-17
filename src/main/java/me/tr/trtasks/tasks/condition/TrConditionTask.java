package me.tr.trtasks.tasks.condition;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.TrTaskOptions;
import me.tr.trtasks.time.TrTime;

import java.util.UUID;

public abstract class TrConditionTask extends TrTask {

    public TrConditionTask(String id, TrTime period, TrTime delay, boolean async, TrConditionTaskOptions options) {
        super(id, period, delay, async, options);
        setNextRun(delay.toMillis());
    }

    public TrConditionTask(TrConditionTaskOptions options, boolean async) {
        this(UUID.randomUUID().toString(), TrTime.EMPTY, TrTime.EMPTY, async, options);
    }

    public TrConditionTask(TrConditionTaskOptions options) {
        this(UUID.randomUUID().toString(), TrTime.EMPTY, TrTime.EMPTY, false, options);
    }

    public TrConditionTask(String id, TrTime period, TrTime delay, boolean async) {
        this(id, period, delay, async, null);
    }

    public TrConditionTask(String id, TrTime period, TrTime delay) {
        this(id, period, delay, false, null);
    }

    public TrConditionTask(TrTime period, TrTime delay, boolean async) {
        this(UUID.randomUUID().toString(), period, delay, async, null);
    }

    public TrConditionTask(TrTime period, TrTime delay) {
        this(UUID.randomUUID().toString(), period, delay, false, null);
    }

    public TrConditionTask(String id, TrTime period, boolean async) {
        this(id, period, TrTime.EMPTY, async);
    }

    public TrConditionTask(String id, TrTime period) {
        this(id, period, TrTime.EMPTY, false);
    }

    public TrConditionTask(TrTime period, boolean async) {
        this(UUID.randomUUID().toString(), period, TrTime.EMPTY, async);
    }

    public TrConditionTask(TrTime period) {
        this(UUID.randomUUID().toString(), period, TrTime.EMPTY, false);
    }

    public TrConditionTask(String id, boolean async) {
        this(id, TrTime.EMPTY, async);
    }

    public TrConditionTask(String id) {
        this(id, TrTime.EMPTY, false);
    }

    public TrConditionTask(boolean async) {
        this(UUID.randomUUID().toString(), async);
    }

    public TrConditionTask() {
        this(UUID.randomUUID().toString(), false);
    }

    protected abstract void execute();

    public abstract boolean canRun();

    @Override
    protected void task() {
        if (canRun()) {
            execute();
        }
    }


    @Override
    public TrConditionTaskOptions getOptions() {
        TrTaskOptions options = super.getOptions();
        if (!(options instanceof TrConditionTaskOptions)) {
            return setOptions(new TrConditionTaskOptions(this).copy(options));
        }
        return (TrConditionTaskOptions) super.getOptions();
    }
}
