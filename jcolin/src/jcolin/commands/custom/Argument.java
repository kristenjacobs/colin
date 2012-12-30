package jcolin.commands.custom;

import java.util.ArrayList;
import java.util.Collection;

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
}
