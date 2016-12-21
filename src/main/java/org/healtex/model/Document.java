package org.healtex.model;

public class Document {
    private String fileName;
    private String personId;
    private String perPersonDocumentId;

    public Document() {

    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return fileName;
    }

}
