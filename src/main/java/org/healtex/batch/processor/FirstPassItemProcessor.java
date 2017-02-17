package org.healtex.batch.processor;

import org.healtex.controller.NamedEntityExtractor;
import org.healtex.model.AnnotatedDocument;
import org.healtex.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class FirstPassItemProcessor implements ItemProcessor<Document, AnnotatedDocument> {

    private static final Logger log = LoggerFactory.getLogger(FirstPassItemProcessor.class);
    private NamedEntityExtractor namedEntityExtractor;

    public FirstPassItemProcessor() {
        this.namedEntityExtractor = new NamedEntityExtractor();
    }

    @Override
    public AnnotatedDocument process(final Document doc) throws Exception {

        AnnotatedDocument annotatedDoc = new AnnotatedDocument();
        annotatedDoc.setFileName(doc.getFileName());
        annotatedDoc.setPerPersonDocumentId(doc.getPerPersonDocumentId());
        annotatedDoc.setPersonId(doc.getPersonId());
        annotatedDoc.setContent(doc.getContent());
        namedEntityExtractor.run(annotatedDoc.getGateDocument());
        return annotatedDoc;
    }

}
