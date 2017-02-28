package org.healtex.batch.listener;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class SecondPassStepExecutionListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(SecondPassStepExecutionListener.class);

    public void beforeStep(StepExecution stepExecution) {

        JobExecution je =	stepExecution.getJobExecution();
        Collection<StepExecution> ses = je.getStepExecutions();
        for (StepExecution se : ses) {
            if (se.getStepName().equals("step1")) {
                ExecutionContext ec = se.getExecutionContext();
                Object storedContext = ec.get("SKIPPED_FILE_NAMES");
                if (storedContext != null) {
                    HashSet<String> failedNames = (HashSet<String>) storedContext;
                    log.info("Obtained skipped file names: {}", storedContext.toString());
                    // TODO this still seems an overkill.. leave it here for now.
                }
            }
        }
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        // do nothing
        return null;
    }
}
