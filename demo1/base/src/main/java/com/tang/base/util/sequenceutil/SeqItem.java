package com.tang.base.util.sequenceutil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * < sequence信息记录包装类 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/16 <br>
 */
public final class SeqItem {

    /**
     * The constant MISSING_DEFINITION.
     */
    public static final SeqItem MISSING_DEFINITION = new SeqItem(null, 0, Long.MAX_VALUE);

    /**
     * The Name.
     */
    final String name;

    /**
     * The Step.
     */
    public final long step;

    /**
     * The Current.
     */
    private AtomicLong current = null;

    /**
     * The Threshold by cached.
     */
    private long thresholdByCached = 0;

    /**
     * The max value of sequence.
     */
    private long maxValue;

    /**
     * The safe threshold
     */
    private long safeThreshold;

    /**
     * Instantiates a new Seq item.
     *
     * @param name the name
     * @param step the step
     * @param maxValue the max value
     */
    public SeqItem(String name, long step, long maxValue) {
        this.name = name;
        this.step = step;
        this.maxValue = maxValue;
        this.safeThreshold = maxValue - step;
    }

    /**
     * Set.
     *
     * @param value the value
     */
    public void set(long value) {
        current = new AtomicLong(value);
        if (value > safeThreshold) {
            thresholdByCached = maxValue;
        }
        else {
            thresholdByCached = value + step;
        }
    }

    /**
     * Next long.
     *
     * @return the long
     */
    public long next() {
        if (current == null) {
            return -1;
        }
        long next = current.getAndIncrement();
        if (next >= thresholdByCached) {
            return -1;
        }
        return next;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SeqItem {" + "name:'" + name + '\'' + ", step:" + step + ", current:" + current + ", thresholdByCached:" + thresholdByCached
            + ", maxValue:" + maxValue + ", safeThreshold:" + safeThreshold + '}';
    }
}
