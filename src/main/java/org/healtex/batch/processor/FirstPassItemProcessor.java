package org.healtex.batch.processor;

import org.healtex.batch.exception.SkipFileException;
import org.healtex.controller.NamedEntityExtractor;
import org.healtex.model.AnnotatedDocument;
import org.healtex.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class FirstPassItemProcessor implements ItemProcessor<Document, AnnotatedDocument> {
    public static final String
            FIRST_PASS_ANNOT_SET_NAME = "passOne";

    private static final Logger log = LoggerFactory.getLogger(FirstPassItemProcessor.class);
    private NamedEntityExtractor namedEntityExtractor;

    public FirstPassItemProcessor() {
        this.namedEntityExtractor = new NamedEntityExtractor();
    }

    @Override
    public AnnotatedDocument process(final Document doc) throws Exception {
        // Catch all exception and replace by SkipFileException (which we explicitly allow the file to skip)
        try {
            AnnotatedDocument annotatedDoc = new AnnotatedDocument();
            annotatedDoc.setFileName(doc.getFileName());
            annotatedDoc.setPerPersonDocumentId(doc.getPerPersonDocumentId());
            annotatedDoc.setPersonId(doc.getPersonId());
            annotatedDoc.setContent(doc.getContent());
            namedEntityExtractor.run(annotatedDoc.getGateDocument());
            return annotatedDoc;
        } catch (Exception ex) { // In the future, change this part to customize how to react to different exceptions
            throw new SkipFileException(ex);
        }
    }

}
