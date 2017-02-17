package org.healtex.pipeline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gate.util.InvalidOffsetException;

/**
 * Saves annotations to annotation set: passOne
 * 
 * Email NER for GATE documents.
 */
public class EmailNER {

	private static final String EMAIL_PATTERN = 
			"[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9- ]+)*@"
		  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.(com|org|edu|gov|mil|co\\.uk|nhs\\.uk))"; //expand
		
	public static void run(gate.Document gateDoc)
	{
		String text = gateDoc.getContent().toString();
		Pattern p= Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);

		while(m.find())
		{
			addAnnotation(gateDoc, m.start(), m.end());
		}
	}
	
	/**
	 * TODO: refactor (see UrlNER)
	 * 
	 * @param gateDoc
	 * @param start
	 * @param end
	 */
	private static void addAnnotation(gate.Document gateDoc, int start, int end)
	{
		gate.FeatureMap gateMap = gate.Factory.newFeatureMap();
		gateMap.put("CATEGORY", "CONTACT");
	
		    try {
				gateDoc.getAnnotations("passOne").add((long)start, (long)end, "EMAIL", gateMap);
		    } catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InvalidOffsetException e) {
				e.printStackTrace();
			}		    
	}
}
