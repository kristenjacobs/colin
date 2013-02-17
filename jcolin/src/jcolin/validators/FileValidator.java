package jcolin.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command.ArgType;
import jcolin.utils.RngUtils;

public class FileValidator extends Validator {
    private String m_schema;
    private String m_shouldExist;

	public FileValidator(String schema, String shouldExist) {
		m_schema = schema;			
		m_shouldExist = shouldExist;
	}

	@Override
	public ArgType getArgType() {
		return ArgType.FILE;
	}

	@Override
	public boolean validate(String value) {
		try {
			if (!m_schema.equals("")) {
				// A schema file has been specified, so we must
				// validate the given file against this.
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
			// No schema file has been specified so the most 
			// we can do is check that the file exists (if required).
			if ((m_shouldExist != null) && m_shouldExist.equals("true")) {
    			return new File(value).exists();
			}
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
