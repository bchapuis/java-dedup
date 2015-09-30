package io.sysmic.dedup.elasticsearch;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.Reader;

public class ChunkerAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String s, Reader reader) {
        Tokenizer tokenizer = new ChunkerTokenizer(reader);
        return new TokenStreamComponents(tokenizer);
    }

}
