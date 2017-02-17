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

    public DeidentifiedDocumentWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public final void write(List<? extends GATEDocument> documents) throws Exception {

        for (GATEDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getFileName())))) {
                bw.write(doc.getAnnotatedContent());
                // TODO: Get patientId from doc and remove related patient dictionary (gazetteer) (if any)

            }

        }
    }

}
