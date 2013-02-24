package jcolin.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class ScriptStep implements Step {
	private String m_name;
	private String m_args;
	private String m_var;
	
	public ScriptStep(String name, String args, String var) {
		m_name = name;
		m_args = args;
		m_var = var;
	}
	
	@Override
	public boolean perform(Shell shell, Console console,
			Map<String, String> environment, Object model) throws Exception {
		
		String output = shell.sourceInternalScript(m_name, getArgsArray(), model, console);
		if (!m_var.equals("")) {
			environment.put(m_var, output);			
		}
		return true;
	}
	
	@Override
	public String getFailureReason() {
		assert(false);
		return null;
	}
	
	private String[] getArgsArray() {
		Collection<String> array = new ArrayList<String>();
		for (String str : m_args.split(" ")) {
			String arg = str.trim();
			if (!arg.equals("")) {
				array.add(arg);
			}
		}
		return array.toArray(new String[]{});
	}
}
