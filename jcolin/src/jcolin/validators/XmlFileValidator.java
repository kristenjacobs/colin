package jcolin.validators;

public class XmlFileValidator implements Validator {
    private String m_schemaFile;

	public XmlFileValidator(String schemaFile) {
		m_schemaFile = schemaFile;			
	}

	public boolean validate(String value) {
		// TODO: Need to use the rng file here!
		return false;
	}
}
