package com.tang.base.util.sequenceutil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/16 <br>
 */
public final class SequenceCache {
    /**
     * The Seq cache.
     */
    public static final Map<String, SeqItem> seqCache = new ConcurrentHashMap<>();
}
