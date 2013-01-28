package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

public class BooleanValidator extends Validator {

	@Override
	String getTypeName() {
		return "boolean";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		return new ArrayList<ValidatorAttribute>();
	}
}
