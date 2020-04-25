package com.tang.base.util.splitWords;

import com.chenlb.mmseg4j.example.Complex;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author tang 分词工具
 */
public final class SplitSentences {

    private SplitSentences() {

    }

    /**
     * @param input               input是输入，允许任何语言和格式形式。对input进行中文分词，去掉停用词和感叹词
     * @param stopWordsDictionary 可以在初始化时在 words中指定停用词列表,可空,使用默认值
     * @return 该类中包装了对结果的多个操作
     */
    public static Words splitSentences(String input, List<String> stopWordsDictionary) {
        Complex complex = new Complex();
        String result;
        try {
            result = complex.segWords(input, "|");
        } catch (IOException e) {
            result = "";
        }
        Words words = new Words(stopWordsDictionary, Arrays.asList(result.split("\\|")));
        return words;
    }


}
