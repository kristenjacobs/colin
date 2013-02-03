package jcolin.validators;

import org.w3c.dom.Element;

public class ValidatorFactory {

	public static Validator create(Element element) throws Exception {
    	String nodeName = element.getNodeName();
    	
    	if (nodeName.equals("String")) {
        	String pattern = element.getAttribute("pattern");
    		return new StringValidator(pattern);
    		
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

    	} else if (nodeName.equals("File")) {
        	String schema = element.getAttribute("schema");
        	return new FileValidator(schema);

    	} else {
    		throw new Exception("Unknown validator: " + nodeName);
    	}
	}
}
