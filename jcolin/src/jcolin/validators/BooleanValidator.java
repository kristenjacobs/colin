package jcolin.validators;

public class BooleanValidator implements Validator {

	public boolean validate(String value) {
		String lower = value.toLowerCase();
		return ((lower.equals("true")) || 
			    (lower.equals("false")));	
	}
}
