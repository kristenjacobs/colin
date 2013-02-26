package jcolin.validators;

import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jcolin.commands.Command.ArgType;
import jcolin.utils.DomUtils;
import jcolin.utils.RngUtils;

public abstract class Validator {
    
    abstract String getTypeName();
    abstract Collection<ValidatorAttribute> getAttributes();
    abstract public ArgType getArgType();

    public boolean validate(String value) {
        try {
            String xml = createXml(value);      
            String rng = createRng();           
            return RngUtils.validate(xml, rng);
            
        } catch (Exception e) {
            return false;
        }               
    }
            
    private String createXml(String value) throws Exception {
        Document argDoc = DomUtils.createDocument();
        Element argElement = DomUtils.addElement(argDoc, argDoc, "argument");
        DomUtils.addText(argDoc, argElement, value);
        return DomUtils.output(argDoc);     
    }

    private String createRng() throws Exception {
        Document rngDoc = DomUtils.createDocument();
        Element rngElement = DomUtils.addElement(rngDoc, rngDoc, "element");
        DomUtils.addAttribute(rngDoc, rngElement, "name", "argument");
        DomUtils.addAttribute(rngDoc, rngElement, "xmlns", "http://relaxng.org/ns/structure/1.0");
        DomUtils.addAttribute(rngDoc, rngElement, "datatypeLibrary", "http://www.w3.org/2001/XMLSchema-datatypes");
        Element data = DomUtils.addElement(rngDoc, rngElement, "data");
        DomUtils.addAttribute(rngDoc, data, "type", getTypeName());
        for (ValidatorAttribute attribute : getAttributes()) {
            Element min = DomUtils.addElement(rngDoc, data, "param");
            DomUtils.addAttribute(rngDoc, min, "name", attribute.m_name);
            DomUtils.addText(rngDoc, min, attribute.m_value);                           
        }
        return DomUtils.output(rngDoc);
    }   
}
