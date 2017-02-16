/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.healtex.gui;

import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 *
 * @author rich
 */
public class GuiHelper {
        public Collection<File> getFileList(String dir, String regex) {
        IOFileFilter fileFiler = new RegexFileFilter(regex);
        Collection<File> files = FileUtils.listFiles(new File(dir),fileFiler,DirectoryFileFilter.DIRECTORY);
        return files;
    }
}
