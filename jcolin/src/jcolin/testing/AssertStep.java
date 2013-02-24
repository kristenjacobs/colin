package jcolin.testing;

import java.util.Map;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public abstract class AssertStep implements Step {
	private String m_var;
	private String m_value;
	private String m_failureReason;
	
	public abstract boolean doCompare(String value, String expected);
	
	public AssertStep(String var, String value) {
		m_var = var;
		m_value = value;
	}
	
	@Override
	public boolean perform(Shell shell, Console console,
			Map<String, String> environment, Object model) throws Exception {

		String output = environment.get(m_var);
		if (output != null) {
			if (!doCompare(output, m_value)) {
				m_failureReason = String.format(
						"Value of variable ('%s' = '%s') doesnt match the expected value of '%s'",
						m_var, output, m_value);						
				return false;
			}
			return true;
		}
		throw new Exception("Value for variable: " + m_var + 
				" has not be found in the test environment");	
	}
	
	@Override
	public String getFailureReason() {
		return m_failureReason;
	}
}
