package org.healtex.model;

public class Document {
    private String fileName;
    private String personId;
    private String perPersonDocumentId;
    private String content;

    public Document() {

    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPerPersonDocumentId(String perPersonDocumentId) {
        this.perPersonDocumentId = perPersonDocumentId;
    }

    public String getPerPersonDocumentId() {
        return perPersonDocumentId;
    }

    @Override
    public String toString() {
        return fileName;
    }

}
