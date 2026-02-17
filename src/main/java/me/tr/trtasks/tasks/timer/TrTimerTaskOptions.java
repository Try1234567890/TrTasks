package me.tr.trtasks.tasks.timer;

import me.tr.trtasks.tasks.TrTaskOptions;
import me.tr.trtasks.time.TrTimeUnit;

public class TrTimerTaskOptions extends TrTaskOptions {
    private String message;
    private TrTimeUnit unit;

    public TrTimerTaskOptions(TrTimerTask task) {
        super(task);
        this.message = "Task " + task.id() + " completed in [Elapsed] [ElapsedUnit]";
        this.unit = TrTimeUnit.MILLISECONDS;
    }

    public String getMessage() {
        return message;
    }

    public TrTimerTaskOptions setMessage(String message) {
        this.message = message;
        return this;
    }

    public TrTimeUnit getUnit() {
        return unit;
    }

    public TrTimerTaskOptions setUnit(TrTimeUnit unit) {
        this.unit = unit;
        return this;
    }

    public TrTimerTaskOptions copy(TrTimerTaskOptions options) {
        super.copy(options);
        setMessage(options.getMessage());
        setUnit(options.getUnit());
        return this;
    }

    @Override
    public TrTimerTaskOptions copy(TrTaskOptions options) {
        super.copy(options);
        return this;
    }
}
