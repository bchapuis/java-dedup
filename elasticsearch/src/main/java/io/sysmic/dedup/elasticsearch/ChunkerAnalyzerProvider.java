package io.sysmic.dedup.elasticsearch;

import io.sysmic.dedup.lucene.ChunkerAnalyzer;
import  org.elasticsearch.common.inject.Inject;
import  org.elasticsearch.common.inject.assistedinject.Assisted;
import  org.elasticsearch.common.settings.Settings;
import  org.elasticsearch.env.Environment;
import  org.elasticsearch.index.Index;
import  org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import  org.elasticsearch.index.settings.IndexSettings;
import  java.io.IOException;

public class ChunkerAnalyzerProvider extends AbstractIndexAnalyzerProvider<ChunkerAnalyzer> {

    public static final String NAME = "chunker";

    protected ChunkerAnalyzer analyzer = new ChunkerAnalyzer();

    @Inject
    public ChunkerAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) throws IOException {
        super(index, indexSettings, name, settings);
    }

    public ChunkerAnalyzer get() {
        return this.analyzer;
    }

}