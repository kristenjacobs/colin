package jcolin.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import jcolin.consoles.TestConsole;
import jcolin.shell.Shell;
import jcolin.utils.ScriptUtils;

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
	public void perform(Shell shell, TestConsole console,
			Map<String, String> environment, Object model) throws Exception {

		String script = ScriptUtils.locate("JCOLIN_TEST_PATH", m_name);
		if (script == null) {
			throw new Exception("Cannot locate script: " + m_name);
		}
		
		console.clearTestOutput();
		shell.sourceInternalScript(script, getArgsArray(), model, console);
		String output = console.getTestOutput();
		
		if (!m_var.equals("")) {
			environment.put(m_var, output);			
		}
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
