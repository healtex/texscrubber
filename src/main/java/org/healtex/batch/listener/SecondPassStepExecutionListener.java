package org.healtex.batch.listener;

import java.util.ArrayList;
import java.util.Collection;

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
                // TODO: do something with (ArrayList<String>) ec.get("FAILED_LIST");
            }
        }
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        // do nothing
        return null;
    }
}
