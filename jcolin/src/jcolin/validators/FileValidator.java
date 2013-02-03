package jcolin.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import jcolin.utils.RngUtils;

public class FileValidator extends Validator {
    private String m_schema;

	public FileValidator(String schema) {
		m_schema = schema;			
	}

	public boolean validate(String value) {
		try {
			if (!m_schema.equals("")) {
				File schemaFile = new File(m_schema);				
				if (!schemaFile.exists()) {
					return false;
				}
				File valueFile  = new File(value);
				if (!valueFile.exists()) {
					return false;
				}	
	    		return RngUtils.validate(valueFile, schemaFile, null);
			}
			// No schema file has been specified so validation must succeed.
			return true;			
			
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
