package org.healtex.io;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Reads files from user-defined input folder.
 * 
 * Assumptions: 
 * 1. input file: text with UTF-8 encoding.
 * 2. input file name consists of three parts: 1. patient-id and 2. document-id (separated by either a dash "-" or a underscore "_"), and 3. ".txt" extension
 * 3. if (2) is not adhered to, each file shall be consider as a different patient.
 *
 */
public class PatientFileReader {	
	public PatientFileReader(){}

	/**
	 * Get list of filed within a given directory (and its sub-directory).
	 * 
	 * @param inputDirectory
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<File> getDocumentList(File inputDirectory){
	return FileUtils.listFiles(new File(inputDirectory.getAbsolutePath()), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
	}
	
	
}
