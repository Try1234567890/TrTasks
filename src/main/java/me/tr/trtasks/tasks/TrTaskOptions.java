package me.tr.trtasks.tasks;

public class TrTaskOptions {
    private final TrTask task;
    private int maxRun;

    public TrTaskOptions(TrTask task) {
        this.task = task;
        this.maxRun = -1;
    }

    public int getMaxRun() {
        return maxRun;
    }

    public TrTaskOptions setMaxRun(int maxRun) {
        this.maxRun = maxRun;
        return this;
    }

    public TrTask getTask() {
        return task;
    }

    public TrTaskOptions copy(TrTaskOptions options) {
        setMaxRun(options.getMaxRun());
        return this;
    }
}
