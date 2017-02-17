package org.healtex.model;

import gate.AnnotationSet;
import gate.creole.ResourceInstantiationException;

import java.util.ArrayList;
import java.util.List;

// TODO: Should be removed and replaced by gate.Document
public class AnnotatedDocument {
    private String annotatedContent;
    private String fileName;
    private String personId;
    private String perPersonDocumentId;
    private List<String> namedEntities;
    private gate.Document gateDocument;

    public AnnotatedDocument() {
    }

    public void setContent(String content) {
        try {
            this.gateDocument = gate.Factory.newDocument(content);
        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        }
    }


    public String getScrubbedContent() {
        return gateDocument.toXml();
    }

    public String toXml() {
        return gateDocument.toXml();
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

    public List<String> getNamedEntities() {
        List<String> result = new ArrayList<>();
        AnnotationSet annotations = this.gateDocument.getAnnotations("passOne");
        for (gate.Annotation annotation : annotations) {
            result.add(gate.Utils.stringFor(this.gateDocument, annotation));
        }
        return result;
    }

    public gate.Document getGateDocument() {
        return gateDocument;
    }

    public String getContent() {
        return gateDocument.toString();
    }

    public String toString() {
        return fileName;
    }

}
