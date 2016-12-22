package org.healtex.batch.processor;

import java.util.ArrayList;
import java.util.List;

import org.healtex.model.Document;
import org.healtex.model.GATEDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class SecondPassItemProcessor implements ItemProcessor<Document, GATEDocument> {

    private static final Logger log = LoggerFactory.getLogger(SecondPassItemProcessor.class);

    @Override
    public GATEDocument process(final Document doc) throws Exception {
        GATEDocument gateDoc = new GATEDocument();

        // TODO: read NE list from first pass

        gateDoc.setFileName(doc.getFileName());
        gateDoc.setPerPersonDocumentId(doc.getPerPersonDocumentId());
        gateDoc.setPersonId(doc.getPersonId());
        return gateDoc;
    }

}
