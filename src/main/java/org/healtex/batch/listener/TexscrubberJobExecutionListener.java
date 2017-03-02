package org.healtex.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TexscrubberJobExecutionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TexscrubberJobExecutionListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            // Perform removal of all patients gazetteers after successfully completed job
            // TODO Need to replace this hardcoded path by user's config.
            //      remember to be in sync with BatchConfiguration.java
            File gazetteersDir = new File("workspace/dev-test-output");
            File[] files = gazetteersDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() &&
                    (files[i].getName().endsWith(".lst") ||
                     files[i].getName().endsWith(".def"))
                   ) {
                     files[i].delete();
                }
            }
        } else {
            log.info("Job did not complete successfully, temporary files are kept for inspection");
        }
    }
}
