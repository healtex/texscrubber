package org.healtex.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.HashSet;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.stereotype.Component;

import org.healtex.model.Document;
import org.healtex.model.AnnotatedDocument;

@Component
public class FirstPassSkipFileListener extends SkipListenerSupport<Document, AnnotatedDocument> {

    private static Set<String> skippedFileNames = new HashSet<String>();

    public static Set<String> getSkippedFileNames() {
        return FirstPassSkipFileListener.skippedFileNames;
    }

    private static final Logger log = LoggerFactory.getLogger(FirstPassSkipFileListener.class);

    @Override
    public void onSkipInRead(java.lang.Throwable t) {
        // TODO how to obtain the file name if read fails?
    }

    @Override
    public void onSkipInProcess(Document item, java.lang.Throwable t) {
        log.info("{} has been skipped in process()", item.getFileName());
        FirstPassSkipFileListener.skippedFileNames.add(item.getFileName());
    }

    @Override
    public void onSkipInWrite(AnnotatedDocument item, java.lang.Throwable t) {
        log.info("{} has been skipped in write()", item.getFileName());
        FirstPassSkipFileListener.skippedFileNames.add(item.getFileName());
    }
}
