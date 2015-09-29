package io.sysmic.dedup.elasticsearch;

import org.elasticsearch.index.analysis.AnalysisModule;

public class ChunkerBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer(ChunkerAnalyzerProvider.NAME, ChunkerAnalyzerProvider.class);
    }

}
