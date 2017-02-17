package org.healtex.batch.writer;


import org.healtex.model.GATEDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class DeidentifiedDocumentWriter implements ItemWriter<GATEDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(DeidentifiedDocumentWriter.class);

    private String outputPath;
    private String gazetteersPath;

    public DeidentifiedDocumentWriter(String outputPath, String gazetteersPath) {
        this.outputPath = outputPath;
        this.gazetteersPath = gazetteersPath;
    }

    @Override
    public final void write(List<? extends GATEDocument> documents) throws Exception {

        // Write results to documents
        for (GATEDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getFileName())))) {
                bw.write(doc.getAnnotatedContent());
            }
        }

        // Remove all patients gazetteers
        for (GATEDocument doc : documents) {
            File f = new File(gazetteersPath + File.separator + doc.getPersonId() + ".lst");
            f.delete();
        }

    }

}
