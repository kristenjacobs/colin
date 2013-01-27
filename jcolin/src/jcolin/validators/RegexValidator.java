package jcolin.validators;

public class RegexValidator implements Validator {
    private String m_pattern;

	public RegexValidator(String pattern) {
		m_pattern = pattern;			
	}

	public boolean validate(String value) {
		// TODO: Need to create the rng file here!
		return false;
	}
}
