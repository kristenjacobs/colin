package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class RegexValidator extends Validator {
    private String m_pattern;

	public RegexValidator(String pattern) {
		m_pattern = pattern;			
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
