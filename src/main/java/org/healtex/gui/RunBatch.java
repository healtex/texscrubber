package org.healtex.gui;

import gate.corpora.DocumentStaxUtils;
import gate.creole.ResourceInstantiationException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rich on 16/02/17.
 */
public class RunBatch extends SwingWorker<Void, String> implements PropertyChangeListener {

    private Collection<File> filesToProcess;
    private NamedEntityExtractor namedEntityExtractor;
    private String pathPrefix;
    private JProgressBar jProgressBarBatchProgress;
    private JButton jButtonLaunchBatch;
    private JButton jButtonCancelBatch;
    Logger logger = LoggerFactory.getLogger(RunBatch.class);

    public RunBatch(Collection<File> filesToProcess,
                    String pathPrefix,
                    JProgressBar jProgressBarBatchProgress,
                    JButton jButtonLaunchBatch,
                    JButton jButtonCancelBatch){
        this.filesToProcess = filesToProcess;
        this.pathPrefix = pathPrefix;
        this.namedEntityExtractor = new NamedEntityExtractor();
        this.jProgressBarBatchProgress = jProgressBarBatchProgress;
        this.jButtonCancelBatch = jButtonCancelBatch;
        this.jButtonLaunchBatch = jButtonLaunchBatch;

        this.jProgressBarBatchProgress.setMaximum(this.filesToProcess.size());
    }

    @Override
    public Void doInBackground() {
        int progress = 0;
        setProgress(0);

        for (File file : this.filesToProcess){
            gate.Document doc = null;
            publish(MessageFormat.format("Processing Document {0}",file.getName()));
            try {
                doc = gate.Factory.newDocument(file.toURI().toURL(),"utf-8");
            } catch (ResourceInstantiationException | MalformedURLException e) {
                publish(MessageFormat.format("Failed to load {}", file.getName(), e.getLocalizedMessage()));
            }

            if(doc!=null) namedEntityExtractor.run(doc);

            try {
                DocumentStaxUtils.writeDocument(doc,new File(pathPrefix+doc.getName()));
            } catch (XMLStreamException | IOException e) {
                publish(MessageFormat.format("Failed to save {}", file.getName(), e.getLocalizedMessage()));
            }
            progress += 1;
            setProgress(progress);
        }


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
}