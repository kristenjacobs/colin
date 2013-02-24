package jcolin.commands.custom;

import java.util.Map;

import jcolin.consoles.Console;
import jcolin.shell.Shell;
import jcolin.utils.ScriptUtils;

public class ExtensionExecutor implements Executor {
    private String m_scriptName;
	
	public ExtensionExecutor(String impl) throws Exception {
		m_scriptName = ScriptUtils.locate("JCOLIN_EXT_PATH", impl);
		if (m_scriptName == null) {
			throw new Exception("Cannot locate script: " + impl);
		}		
	}

	public void execute(Shell shell, Object model, 
			Console console, Map<String, String> argsMap) {
		
		shell.sourceInternalScript(m_scriptName, 
				getArgs(argsMap), model, console);
	}
	
	private String[] getArgs(Map<String, String> argsMap) {
		return argsMap.values().toArray(new String[]{});
	}
}
