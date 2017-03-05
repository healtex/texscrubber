package org.healtex.batch.processor;

import gate.*;
import gate.creole.SerialAnalyserController;
import gate.creole.gazetteer.DefaultGazetteer;
import gate.util.GateRuntimeException;
import org.healtex.batch.exception.SkipFileException;
import org.healtex.batch.listener.FirstPassSkipFileListener;
import org.healtex.model.AnnotatedDocument;
import org.healtex.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;

public class SecondPassItemProcessor implements ItemProcessor<Document, AnnotatedDocument> {

    private static final Logger log = LoggerFactory.getLogger(SecondPassItemProcessor.class);
    private String gazetteersPath;

    public static final String
            SECOND_PASS_ANNOT_SET_NAME = "deid";

    public SecondPassItemProcessor(String gazetteersPath) {
        this.gazetteersPath = gazetteersPath;
        this.setUp();
    }

    public void setUp() {
        //make sure the right plugin is loaded
        File pluginsHome = new File("GATE/plugins");
        try {
            Gate.getCreoleRegister().registerDirectories(
                    new File(pluginsHome, "ANNIE").toURI().toURL());
        } catch (Exception e) {
            throw new GateRuntimeException(e);
        }

    }

    @Override
    public AnnotatedDocument process(final Document doc) throws Exception {
        if (FirstPassSkipFileListener.getSkippedFileNames().contains(doc.getFileName())) {
            throw new SkipFileException(doc.getFileName() + " has been skipped in first pass of the job, will be skipped in second pass as well.");
            // TODO potentially, instead of skipping the entire pipeline for this file, maybe we write a dummy file to tell the user this file failed?
        }
        AnnotatedDocument annotatedDoc = new AnnotatedDocument();

        annotatedDoc.setFileName(doc.getFileName());
        annotatedDoc.setPerPersonDocumentId(doc.getPerPersonDocumentId());
        annotatedDoc.setPersonId(doc.getPersonId());
        annotatedDoc.setContent(doc.getContent());
        String gztPath = this.gazetteersPath + File.separator + annotatedDoc.getPersonId() + ".def";


        FeatureMap params = Factory.newFeatureMap();
        params.put(gate.creole.gazetteer.DefaultGazetteer.DEF_GAZ_LISTS_URL_PARAMETER_NAME,
                new File(gztPath).toURI().toURL());
        params.put(DefaultGazetteer.DEF_GAZ_ANNOT_SET_PARAMETER_NAME, SecondPassItemProcessor.SECOND_PASS_ANNOT_SET_NAME);
        LanguageAnalyser gzt = (LanguageAnalyser) Factory.createResource("gate.creole.gazetteer.DefaultGazetteer", params);

        SerialAnalyserController ner = (SerialAnalyserController) Factory.createResource("gate.creole.SerialAnalyserController");
        ner.add((gate.LanguageAnalyser) Factory.createResource("gate.creole.tokeniser.DefaultTokeniser"));
        ner.add((gate.LanguageAnalyser) Factory.createResource("gate.creole.splitter.SentenceSplitter"));
        ner.add(gzt);
        Corpus corpus1 = Factory.newCorpus("c1");
        corpus1.add(annotatedDoc.getGateDocument());
        ner.setCorpus(corpus1);
        ner.execute();
        return annotatedDoc;
    }

}
