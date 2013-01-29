package jcolin.validators;

import org.w3c.dom.Element;

public class ValidatorFactory {

	public static Validator create(Element element) throws Exception {
    	String nodeName = element.getNodeName();
    	
    	if (nodeName.equals("String")) {
    		return new StringValidator();
    		
    	} else if (nodeName.equals("Integer")) {
        	String min = element.getAttribute("min");
        	String max = element.getAttribute("max");
    		return new IntegerValidator(min, max);

    	} else if (nodeName.equals("Double")) {
        	String min = element.getAttribute("min");
        	String max = element.getAttribute("max");
    		return new DoubleValidator(min, max);

    	} else if (nodeName.equals("Boolean")) {
        	return new BooleanValidator();

    	} else if (nodeName.equals("Regex")) {
        	String pattern = element.getAttribute("pattern");
        	return new RegexValidator(pattern);

    	} else if (nodeName.equals("XmlFile")) {
        	String schemaFile = element.getAttribute("schemaFile");
        	return new XmlFileValidator(schemaFile);

    	} else {
    		throw new Exception("Unknown validator: " + nodeName);
    	}
	}
}
