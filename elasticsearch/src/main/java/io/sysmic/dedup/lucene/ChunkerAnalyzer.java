package io.sysmic.dedup.lucene;

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
