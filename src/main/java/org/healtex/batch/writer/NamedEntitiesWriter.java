package org.healtex.batch.writer;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.healtex.model.GATEDocument;

public class NamedEntitiesWriter implements ItemWriter<GATEDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(NamedEntitiesWriter.class);

    private String outputPath;

    public NamedEntitiesWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public final void write(List<? extends GATEDocument> documents) throws Exception {
        // TODO: 1. Create (if not exist) temporary folder for patient dictionaries (gazetteers)
        for (GATEDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getFileName())))) {
                bw.write(doc.getNamedEntities().toString());
                // TODO: 2. Get patientId from doc and then append all named entities into patient dictionary file
            }
        }
    }

}
