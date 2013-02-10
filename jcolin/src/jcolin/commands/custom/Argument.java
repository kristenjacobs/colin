package jcolin.commands.custom;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command.ArgType;
import jcolin.validators.Validator;

public class Argument {
    private String m_name;
	private Collection<Validator> m_validators;
    
	public Argument(String name) {
		m_name = name;
		m_validators = new ArrayList<Validator>();
	}
	
	public String getName() {
		return m_name;
	}
	
	public void addValidator(Validator validator) {
		m_validators.add(validator);
	}
	
	public boolean validate(String value) {
		for (Validator validator : m_validators) {
			if (!validator.validate(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method returns the ArgType for this argument which is used
	 * for tab completion purposes. As we can have more than a single 
	 * type/validator per arument, we choose the order of the ArgType
	 * enum to be the priority mechanism.
	 */
	public ArgType getArgType() {
		for (ArgType argType : ArgType.values()) {
			for (Validator validator : m_validators) {
				if (validator.getArgType() == argType) {
					return argType;
				}
			}
		}
        return ArgType.IGNORE;		
	}
}
