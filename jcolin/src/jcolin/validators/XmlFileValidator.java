package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class XmlFileValidator extends Validator {
    private String m_schemaFile;

	public XmlFileValidator(String schemaFile) {
		m_schemaFile = schemaFile;			
	}

	@Override
	String getTypeName() {
		return "string";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		return new ArrayList<ValidatorAttribute>();
	}
}
