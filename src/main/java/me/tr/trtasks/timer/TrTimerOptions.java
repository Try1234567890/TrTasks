package me.tr.trtasks.timer;

import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.time.TrTimeUnit;

public class TrTimerOptions {
    private final TrTimer timer;
    /**
     * Time to wait before start timer cycle when {@link TrTimer#start()} is called.<p>
     * Def: {@link TrTime#EMPTY}
     */
    private TrTime delay;
    /**
     * Time to wait between runs in timer cycle.<p>
     * Def: {@code 1 Millisecond}
     */
    private TrTime period;


    public TrTimerOptions(TrTimer timer) {
        this.timer = timer;
        this.delay = TrTime.EMPTY;
        this.period = new TrTime(1, TrTimeUnit.MILLISECONDS);
    }

    public TrTimer getTimer() {
        return timer;
    }

    public TrTime getDelay() {
        return delay;
    }

    public TrTimerOptions setDelay(TrTime delay) {
        this.delay = delay;
        return this;
    }

    public TrTime getPeriod() {
        return period;
    }

    public TrTimerOptions setPeriod(TrTime period) {
        this.period = period;
        return this;
    }
}
