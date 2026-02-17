package me.tr.trtasks.tasks.condition;


import me.tr.trtasks.tasks.TrTaskOptions;

public class TrConditionTaskOptions extends TrTaskOptions {

    public TrConditionTaskOptions(TrConditionTask task) {
        super(task);
    }

    @Override
    public TrConditionTaskOptions copy(TrTaskOptions options) {
        super.copy(options);
        return this;
    }
}
