package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class IntegerValidator extends Validator {
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

	@Override
	String getTypeName() {
		return "integer";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		Collection<ValidatorAttribute> attributes = new ArrayList<ValidatorAttribute>();
		if (m_hasMin) attributes.add(new ValidatorAttribute("minInclusive", m_min));
		if (m_hasMax) attributes.add(new ValidatorAttribute("maxInclusive", m_max));
		return attributes;
	}
}

	