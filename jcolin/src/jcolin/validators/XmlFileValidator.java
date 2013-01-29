package jcolin.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import jcolin.utils.RngUtils;

public class XmlFileValidator extends Validator {
    private String m_schemaFile;

	public XmlFileValidator(String schemaFile) {
		m_schemaFile = schemaFile;			
	}

	public boolean validate(String value) {
		try {
    		return RngUtils.validate(
                new File(m_schemaFile), 
                new File(value),
                null);
			
		} catch (Exception e) {
			return false;
		}				
	}
	
	@Override
	String getTypeName() {
		assert(false);
		return "";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		assert(false);
		return new ArrayList<ValidatorAttribute>();
	}
}
