package org.healtex.batch.processor;

import gate.*;
import gate.creole.SerialAnalyserController;
import gate.util.GateRuntimeException;
import org.healtex.model.AnnotatedDocument;
import org.healtex.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;

public class SecondPassItemProcessor implements ItemProcessor<Document, AnnotatedDocument> {

    private static final Logger log = LoggerFactory.getLogger(SecondPassItemProcessor.class);
    private String gazetteersPath;

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
        AnnotatedDocument annotatedDoc = new AnnotatedDocument();

        annotatedDoc.setFileName(doc.getFileName());
        annotatedDoc.setPerPersonDocumentId(doc.getPerPersonDocumentId());
        annotatedDoc.setPersonId(doc.getPersonId());
        annotatedDoc.setContent(doc.getContent());
        String gztPath = this.gazetteersPath + File.separator + annotatedDoc.getPersonId() + ".def";


        FeatureMap params = Factory.newFeatureMap();
        params.put(gate.creole.gazetteer.DefaultGazetteer.DEF_GAZ_LISTS_URL_PARAMETER_NAME,
                new File(gztPath).toURI().toURL());
        LanguageAnalyser gzt = (LanguageAnalyser) Factory.createResource("gate.creole.gazetteer.DefaultGazetteer", params);

        SerialAnalyserController ner = (SerialAnalyserController) Factory.createResource("gate.creole.SerialAnalyserController");
        ner.add((gate.LanguageAnalyser) Factory.createResource("gate.creole.tokeniser.DefaultTokeniser"));
        ner.add((gate.LanguageAnalyser) Factory.createResource("gate.creole.splitter.SentenceSplitter"));
        ner.add(gzt);
        Corpus corpus1 = Factory.newCorpus("c1");
        corpus1.add(annotatedDoc.getGateDocument());
        ner.setCorpus(corpus1);
        ner.execute();
//        gzt.setDocument(annotatedDoc.getGateDocument());
//        gzt.execute();
        return annotatedDoc;
    }

}
