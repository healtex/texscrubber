package org.healtex.model;

import gate.AnnotationSet;
import gate.creole.ResourceInstantiationException;
import org.healtex.batch.processor.FirstPassItemProcessor;
import org.healtex.batch.processor.SecondPassItemProcessor;
import org.healtex.pipeline.ScrubberProcessor;

import java.util.ArrayList;
import java.util.List;

public class AnnotatedDocument {
    private String fileName;
    private String personId;
    private String perPersonDocumentId;
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

    public String getScrubbedText() {
        return ScrubberProcessor.getDeidedText(gateDocument, SecondPassItemProcessor.SECOND_PASS_ANNOT_SET_NAME, "Lookup");
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
        AnnotationSet annotations = this.gateDocument.getAnnotations(FirstPassItemProcessor.FIRST_PASS_ANNOT_SET_NAME);
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