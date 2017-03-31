package org.healtex.pipeline;

import gate.Corpus;
import gate.CorpusController;
import gate.Factory;
import gate.Gate;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * See GATE/pipeline/preProcessing.xgapp
 */
public class PreProcess {
	
	private static CorpusController preProc;
	private static Corpus corpus;
	
	public PreProcess()
	{		
		PreProcess.init();
	}
	
	public CorpusController getController(){
		return preProc;
	}
	
	private static void initGate(){
		Logger.getRootLogger().setLevel(Level.OFF);
		
		Out.prln("\n.Initialising pipeline ...");
		
		/*
		 * init GATE
		 */
		Gate.setPluginsHome(new File("GATE/"));
		Gate.setGateHome(new File("GATE/"));
		Gate.runInSandbox(true);
		
		try {
			Gate.init();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void init()
	{		
		initGate();
		
		try {				
			/*
			 * Init pre-processing pipeline:
			 * 1.Tokenizer
			 * 2.Sentence splitter 
			 */
			String path = "GATE/pipeline/preProcessing.xgapp";
			preProc = (CorpusController) PersistenceManager.loadObjectFromFile(new File(path));
			corpus = Factory.newCorpus("c1");
			preProc.setCorpus(corpus);
			
		} catch (GateException e) {
			System.err.println("Pipeline.initGate(): " + e.getMessage());
			System.exit(1); 
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Out.prln(".Initialisation completed ...");
	}

	/**
	 * Pre-processing pipeline.
	 * 
	 * @param gateDoc gate.Document
	 */
	public void run(gate.Document gateDoc)
	{		
		try{
			corpus.add(gateDoc); 
			preProc.execute();
		} catch (GateException e) {
			System.err.println("Pipeline.preProcessingPipeline(...): " + e.getMessage() );
		}
		
		//clean(preProc);
		PreProcess.corpus.clear();
	    PreProcess.corpus.cleanup();
	    PreProcess.preProc.cleanup();
	    Factory.deleteResource(corpus);
	    Factory.deleteResource(preProc);
		 
	}
}
