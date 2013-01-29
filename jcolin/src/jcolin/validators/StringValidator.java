package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class StringValidator extends Validator {
    private String m_pattern;
	private boolean m_hasPattern;

	public StringValidator(String pattern) {
		if (!pattern.equals("")) {
			m_pattern = pattern;			
			m_hasPattern = true;
		}
	}

	@Override
	String getTypeName() {
		return "string";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		Collection<ValidatorAttribute> attributes = new ArrayList<ValidatorAttribute>();
		if (m_hasPattern) attributes.add(new ValidatorAttribute("pattern", m_pattern));
		return attributes;
	}	
}
