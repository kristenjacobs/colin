package jcolin.validators;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jcolin.utils.DomUtils;
import jcolin.utils.RngUtils;

public class IntegerValidator implements Validator {
    private String m_min;
    private boolean m_hasMin;
    private String m_max;
    private boolean m_hasMax;

	public IntegerValidator(String min, String max) {
		if (!min.equals("")) {
			m_min = min;
			m_hasMin = true;			
		}
		if (!max.equals("")) {
			m_max = max;
			m_hasMax = true;			
		}
	}

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
	    DomUtils.addAttribute(rngDoc, data, "type", "integer");
	    if (m_hasMin) {
		    Element min = DomUtils.addElement(rngDoc, data, "param");
		    DomUtils.addAttribute(rngDoc, min, "name", "minInclusive");
		    DomUtils.addText(rngDoc, min, m_min);   	    	
	    }
	    if (m_hasMax) {
		    Element max = DomUtils.addElement(rngDoc, data, "param");
		    DomUtils.addAttribute(rngDoc, max, "name", "maxInclusive");
		    DomUtils.addText(rngDoc, max, m_max);	    	
	    }
	    return DomUtils.output(rngDoc);		
	}
}

	