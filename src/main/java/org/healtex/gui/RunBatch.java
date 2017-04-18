package org.healtex.gui;

import gate.corpora.DocumentStaxUtils;
import gate.creole.ResourceInstantiationException;
import org.healtex.batch.BatchConfiguration;
import org.healtex.controller.NamedEntityExtractor;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

/**
 * Created by rich on 16/02/17.
 */
@SpringBootApplication
public class RunBatch extends SwingWorker<Void, String> implements PropertyChangeListener {

    private Collection<File> filesToProcess;
    private NamedEntityExtractor namedEntityExtractor;
    private File pathPrefix;
    private JProgressBar jProgressBarBatchProgress;
    private JButton jButtonLaunchBatch;
    private JButton jButtonCancelBatch;
    private String inputDir;
    private String workingDir;
    private String outputDir;



    Logger logger = LoggerFactory.getLogger(RunBatch.class);

    public RunBatch(Collection<File> filesToProcess,
                    File pathPrefix,
                    JProgressBar jProgressBarBatchProgress,
                    JButton jButtonLaunchBatch,
                    JButton jButtonCancelBatch){
        //not used
//        this.filesToProcess = filesToProcess;
        this.pathPrefix = pathPrefix;
        this.namedEntityExtractor = new NamedEntityExtractor();
        this.jProgressBarBatchProgress = jProgressBarBatchProgress;
        this.jButtonCancelBatch = jButtonCancelBatch;
        this.jButtonLaunchBatch = jButtonLaunchBatch;

//        this.jProgressBarBatchProgress.setMaximum(this.filesToProcess.size());


    }

    @Override
    public Void doInBackground() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException {
        int progress = 0;
        setProgress(0);

        String[] args = new String[3];
        args[0] = inputDir;
        args[1] = workingDir;
        args[2] = outputDir;


        SpringApplication app = new SpringApplication(BatchConfiguration.class);
        app.run(args).close();


// TODO: implement for progressbar
//        for (File file : this.filesToProcess){
//            gate.Document doc = null;
//            publish(MessageFormat.format("Processing Document {0}",file.getName()));
//            try {
//                doc = gate.Factory.newDocument(file.toURI().toURL(),"utf-8");
//            } catch (ResourceInstantiationException | MalformedURLException e) {
//                publish(MessageFormat.format("Failed to load {}", file.getName(), e.getLocalizedMessage()));
//            }
//
//            if(doc!=null) {
//                namedEntityExtractor.run(doc);
//                try {
//                    DocumentStaxUtils.writeDocument(doc, new File(pathPrefix.getCanonicalPath() + File.separator + doc.getName()));
//                } catch (XMLStreamException | IOException e) {
//                    publish(MessageFormat.format("Failed to save {}", file.getName(), e.getLocalizedMessage()));
//                }
//                gate.Factory.deleteResource(doc);
//            }
//
//            progress += 1;
//            setProgress(progress);
//        }


        return null;
    }

    @Override
    protected void process(List<String> chunks){
        for(String message : chunks){
            logger.info(message);
        }
    }
    @Override
    public void done() {
        jButtonLaunchBatch.setEnabled(true);
        jButtonCancelBatch.setEnabled(false);
        jProgressBarBatchProgress.setValue(0);
//        setCursor(null); //turn off the wait cursor
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBarBatchProgress.setValue(progress);

        }
    }
    public void setWorkingDir(String workingDir) {
        this.workingDir = "--workingDir="+workingDir;
    }

    public void setInputDir(String inputDir) {
        this.inputDir = "--inputDir="+inputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = "--outputDir="+outputDir;
    }
}