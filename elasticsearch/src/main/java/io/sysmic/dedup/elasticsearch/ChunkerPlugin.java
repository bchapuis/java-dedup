package io.sysmic.dedup.elasticsearch;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.Collection;
import java.util.Collections;

public class ChunkerPlugin extends AbstractPlugin {

    public String name() {
        return "analysis-chunk";
    }

    public String description() {
        return "Analyzer that splits a string into chunks.";
    }

    public void onModule(AnalysisModule analysisModule) {
        analysisModule.addProcessor(new ChunkerBinderProcessor());
    }

}
