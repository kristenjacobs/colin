package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class RealValidator extends Validator {
    private String m_min;
    private boolean m_hasMin;
    private String m_max;
    private boolean m_hasMax;

	public RealValidator(String min, String max) {
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
		return "double";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {		
		return new ArrayList<ValidatorAttribute>();
	}
}
