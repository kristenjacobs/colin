package jcolin.validators;

public class RealValidator implements Validator {
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

	public boolean validate(String value) {
		// TODO: Need to create the rng file here.
		return false;
	}
}
