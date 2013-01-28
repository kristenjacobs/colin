package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class StringValidator extends Validator {

	@Override
	String getTypeName() {
		return "string";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		return new ArrayList<ValidatorAttribute>();
	}
}
