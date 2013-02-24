package jcolin.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jcolin.consoles.TestConsole;
import jcolin.shell.Shell;

public class CommandStep implements Step {
	private String m_name;
	private String m_var;
	
	public CommandStep(String name, String var) {
		m_name = name;
		m_var = var;
	}
	
	@Override
	public void perform(Shell shell, TestConsole console,
			Map<String, String> environment, Object model) throws Exception {		
		
		String output = shell.execute(getCommandArray(), model, console);
		if (!m_var.equals("")) {
			environment.put(m_var, output);			
		}
	}
	
	@Override
	public String getFailureReason() {
		assert(false);
		return null;
	}
	
	private String[] getCommandArray() {
		Collection<String> array = new ArrayList<String>();
		for (String str : m_name.split(" ")) {
			String arg = str.trim();
			if (!arg.equals("")) {
				array.add(arg);
			}
		}
		return array.toArray(new String[]{});
	}
}
