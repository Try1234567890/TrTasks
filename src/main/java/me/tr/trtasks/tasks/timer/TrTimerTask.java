package me.tr.trtasks.tasks.timer;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.TrTaskOptions;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.time.TrTimeUnit;

import java.util.function.Supplier;

public class TrTimerTask extends TrTask {

    public TrTimerTask(TrTimerTaskOptions options) {
        super(options);
    }

    public TrTimerTask() {
        this(null);
    }

    public <T> T execute(Supplier<T> supplier) {
        long start = System.nanoTime();

        T result = supplier.get();

        long end = System.nanoTime();

        TrTimeUnit unit = getOptions().getUnit();
        long elapsed = new TrTime(end - start, TrTimeUnit.NANOSECONDS).to(unit);

        sendMessage(getOptions().getMessage()
                .replace("[Elapsed]", String.valueOf(elapsed))
                .replace("[ElapsedUnit]", unit.getSymbols()[0])
                .replace("[ElapsedUnit0]", unit.getSymbols()[0])
                .replace("[ElapsedUnit1]", unit.getSymbols()[1])
                .replace("[ElapsedUnit2]", unit.getSymbols()[2])
                .replace("[Start]", String.valueOf(start))
                .replace("[End]", String.valueOf(end))
        );

        return result;
    }

    protected void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public TrTimerTaskOptions getOptions() {
        TrTaskOptions options = super.getOptions();
        if (!(options instanceof TrTimerTaskOptions)) {
            return setOptions(new TrTimerTaskOptions(this).copy(options));
        }
        return (TrTimerTaskOptions) super.getOptions();
    }

    @Override
    protected void task() {
    }
}
