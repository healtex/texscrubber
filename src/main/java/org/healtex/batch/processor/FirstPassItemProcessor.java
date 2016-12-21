package org.healtex.batch.processor;

import org.healtex.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class FirstPassItemProcessor implements ItemProcessor<Document, Document> {

    private static final Logger log = LoggerFactory.getLogger(FirstPassItemProcessor.class);

    @Override
    public Document process(final Document doc) throws Exception {
        return doc;
    }

}
