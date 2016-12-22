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
        log.info("Modify the step execution");
        ExecutionContext ec = stepExecution.getExecutionContext();

        ArrayList<String> als = new ArrayList<String>();
        ec.put("FAILED_LIST", als);
        // TODO: Where to get list of failed id
        // 1. task onError listener?
        // 2. or processor remember it

        // persist it someway
        return null;
    }
}
