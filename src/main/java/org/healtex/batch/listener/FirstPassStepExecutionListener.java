package org.healtex.batch.listener;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class FirstPassStepExecutionListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(FirstPassStepExecutionListener.class);

    public void beforeStep(StepExecution stepExecution) {
        // do nothing
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext ec = stepExecution.getExecutionContext();
        // Remarks:
        //   Actually, if we use a static variable in FirstPassSkipFileListener
        //   to store the names, it is overkill to use StepExecutionListener to
        //   "pass" the names from step1 to step2. However, initially,
        //   we took this approach because it may(?) work well in multi-host job.
        //   Still, doing it this way has one advantage: the names are recorded
        //   in the DB (if a job repo is used) for future checks.
        ec.put("SKIPPED_FILE_NAMES",
               FirstPassSkipFileListener.getSkippedFileNames());
        return null;
    }
}
