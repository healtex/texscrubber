package org.healtex.batch.writer;


import org.healtex.model.AnnotatedDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class NamedEntitiesWriter implements ItemWriter<AnnotatedDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(NamedEntitiesWriter.class);

    private String outputPath;

    public NamedEntitiesWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public final void write(List<? extends AnnotatedDocument> documents) throws Exception {

        List<String> personIds = new ArrayList<>();
        // Clear person gazetteers
        for (AnnotatedDocument doc : documents) {
            personIds.add(doc.getPersonId());
        }

        // TODO: Create sets of NE per person per CHUNK
        // Append to person gazetteers
        for (AnnotatedDocument doc : documents) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(outputPath + File.separator + doc.getPersonId() + ".lst"), true))) {
                for (String entity : doc.getNamedEntities()) {
                    bw.write(entity + "\n");
                }
            }
        }

        // TODO: Check whether the file is already exist, then skip

        for (String personId : personIds) {
            File f = new File(outputPath + File.separator + personId + ".def");
            if (f.exists() && !f.isDirectory()) {
                continue;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(personId + ".lst:deid:deid\n");
            }
        }
    }

}
