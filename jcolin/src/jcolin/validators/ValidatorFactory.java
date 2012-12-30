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

    	} else if (nodeName.equals("Boolean")) {
        	return new BooleanValidator();

    	} else {
    		throw new Exception("Unknown validator: " + nodeName);
    	}
	}
}
