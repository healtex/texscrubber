package org.healtex.batch.writer;


import org.healtex.model.GATEDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class NamedEntitiesWriter implements ItemWriter<GATEDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(NamedEntitiesWriter.class);

    private String outputPath;

    public NamedEntitiesWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public final void write(List<? extends GATEDocument> documents) throws Exception {

        // Clear person gazetteers
        for (GATEDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getPersonId() + ".lst")))) {
                bw.write("");
            }
        }
        // Append to person gazetteers
        for (GATEDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getPersonId() + ".lst"), true))) {
                for (String entity : doc.getNamedEntities()) {
                    bw.write(entity + "\n");
                }
            }
        }
    }

}
