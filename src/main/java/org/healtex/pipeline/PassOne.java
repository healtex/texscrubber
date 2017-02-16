package org.healtex.pipeline;

import java.io.File;
import java.io.IOException;

import gate.Corpus;
import gate.CorpusController;
import gate.Factory;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

/**
 * Saves annotations to annotation set: passOne
 *
 * See GATE/pipeline/uk_version/letters/passOne.xgapp
 */
public class PassOne {
	
	private static CorpusController passOne_Letters;
	private static Corpus corpus_Letters;
	
	public PassOne(){
		PassOne.init();
	}
	
	public CorpusController getController(){
		return passOne_Letters;
	}
	
	private static void init(){
		
		/*
		 * initialize first pass pipeline, data type: UK Letters
		 */
		
		String path2 = "GATE/pipeline/uk_version/letters/passOne.xgapp";
		try {
			PassOne.passOne_Letters =(CorpusController)PersistenceManager.loadObjectFromFile(new File(path2));
			PassOne.corpus_Letters = Factory.newCorpus("c2");
			PassOne.passOne_Letters.setCorpus(corpus_Letters);
			
		} catch (GateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * FirstPass pipeline: for UK clinical letters.
	 * 
	 * @param gateDoc gate.Document
	 */
	public void firstPassPipeline_Letter(gate.Document gateDoc)
	{		
		try{
	  		PassOne.corpus_Letters.add(gateDoc); 
			PassOne.passOne_Letters.execute();
		} catch (GateException e) {
			System.err.println("Pipeline.firstPassPipeline_UkLetters(...): " + e.getMessage() );
		}
		PassOne.corpus_Letters.clear();
		PassOne.corpus_Letters.cleanup();
		PassOne.passOne_Letters.cleanup();
		Factory.deleteResource(corpus_Letters);
		Factory.deleteResource(passOne_Letters);
		
	}
}