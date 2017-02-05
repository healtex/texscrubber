package org.healtex.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import gate.Annotation;

public class ScrubberProcessor {
	
	/**
	 * Assumptions:
	 * 1. all NEs' identified are contained within the 'annotationSet' and tagged as 'annTag' 
	 * 
	 * Scrubs all annTags' in gateDoc and replaces them with a string of ds'
	 * 
	 * @param gateDoc
	 * @param annotationSet
	 * @param annTag
	 */
	public static String getDeidedText(gate.Document gateDoc, String annotationSet, String annTag){		
		List<Annotation> ann = new ArrayList<Annotation>(gateDoc.getAnnotations(annotationSet).get(annTag));		
		StringBuffer text = new StringBuffer(gateDoc.getContent().toString());
		int length = 0;
		
		for(Annotation a: ann)
		{	
			length = a.getEndNode().getOffset().intValue() - a.getStartNode().getOffset().intValue();
			text.replace(a.getStartNode().getOffset().intValue(), a.getEndNode().getOffset().intValue(), StringUtils.repeat("d", length));
		}
	return new String(text);
	}
}
