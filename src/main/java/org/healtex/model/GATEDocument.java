package org.healtex.model;

import java.util.List;

public class GATEDocument {
    private String annotatedContent;
    private String fileName;
    private String personId;
    private String perPersonDocumentId;
    private List<String> namedEntities;

    public GATEDocument() {

    }

    public void setAnnotatedContent(String annotatedContent) {
        this.annotatedContent = annotatedContent;
    }

    public String getAnnotatedContent() {
        return annotatedContent;
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

    public void setNamedEntities(List<String> namedEntities) {
        this.namedEntities = namedEntities;
    }

    public List<String> getNamedEntities() {
        return namedEntities;
    }

    @Override
    public String toString() {
        return fileName;
    }

}
