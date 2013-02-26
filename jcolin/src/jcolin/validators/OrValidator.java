package jcolin.validators;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jcolin.commands.Command.ArgType;

/**
 * The following validator class returns true (i.e. reports the 
 * validation as being successful) if any of its sub-validators
 * return true.
 */
public class OrValidator extends Validator {
    private Collection<Validator> m_validators;

    OrValidator(Element element) throws Exception {
        m_validators = new ArrayList<Validator>();
        NodeList validatorNodes = element.getChildNodes();
        for (int i = 0; i < validatorNodes.getLength(); i++) {
            Node validatorNode = validatorNodes.item(i);
            if (validatorNode instanceof Element) {       
                m_validators.add(ValidatorFactory.create((Element)validatorNode));
            }
        }       
    }
    
    @Override
    public boolean validate(String value) {
        for (Validator validator : m_validators) {
            if (validator.validate(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    String getTypeName() {
        assert(false);
        return "";
    }

    @Override
    Collection<ValidatorAttribute> getAttributes() {
        assert(false);
        return new ArrayList<ValidatorAttribute>();
    }

    @Override
    public ArgType getArgType() {
        return ArgType.IGNORE;
    }
}
