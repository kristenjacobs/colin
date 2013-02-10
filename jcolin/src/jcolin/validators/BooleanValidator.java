package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command.ArgType;

public class BooleanValidator extends Validator {
	
	@Override
	public ArgType getArgType() {
		return ArgType.IGNORE;
	}
	
	@Override
	String getTypeName() {
		return "boolean";
	}

	@Override
	Collection<ValidatorAttribute> getAttributes() {
		return new ArrayList<ValidatorAttribute>();
	}
	
}
