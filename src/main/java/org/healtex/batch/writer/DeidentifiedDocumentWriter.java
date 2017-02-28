package org.healtex.batch.writer;


import org.healtex.model.AnnotatedDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class DeidentifiedDocumentWriter implements ItemWriter<AnnotatedDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(DeidentifiedDocumentWriter.class);

    private String outputPath;
    private String gazetteersPath;

    public DeidentifiedDocumentWriter(String outputPath, String gazetteersPath) {
        this.outputPath = outputPath;
        this.gazetteersPath = gazetteersPath;
    }

    @Override
    public final void write(List<? extends AnnotatedDocument> documents) throws Exception {

        // Write results to documents
        for (AnnotatedDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getFileName().concat(".xml"))))) {
                bw.write(doc.toXml());
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getFileName())))) {
                bw.write(doc.getScrubbedText());
            }
        }

        // TODO: Perform removal of all patients gazetteers after job
//        for (AnnotatedDocument doc : documents) {
//            File f = new File(gazetteersPath + File.separator + doc.getPersonId() + ".lst");
//            f.delete();
//            f = new File(gazetteersPath + File.separator + doc.getPersonId() + ".def");
//            f.delete();
//        }

    }
}