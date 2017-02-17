package org.healtex.controller;
import org.healtex.pipeline.EmailNER;
import org.healtex.pipeline.PassOne;
import org.healtex.pipeline.PreProcess;
import org.healtex.pipeline.UrlNER;


/**
 * 
 * Controller.
 * 
 */
public class NamedEntityExtractor {
	PassOne passOne;
	PreProcess preProcess;
	public NamedEntityExtractor(){
		preProcess = new PreProcess();
		passOne = new PassOne();
	}
	
	/**
	 * Run NLP components:
	 * 1. Pre-processing
	 * 2. PassOne
	 * 
	 * @param gateDoc
	 * 
	 */
	public void run(gate.Document gateDoc){
		preProcess.run(gateDoc);				
		passOne.firstPassPipeline_Letter(gateDoc);
		UrlNER.run(gateDoc);
		EmailNER.run(gateDoc);
	}
	
}