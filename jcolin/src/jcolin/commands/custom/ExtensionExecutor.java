package jcolin.commands.custom;

import java.io.File;
import java.util.Map;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class ExtensionExecutor implements Executor {
    private String m_scriptName;
	
	public ExtensionExecutor(String impl) throws Exception {
		m_scriptName = locateScript(impl);
		if (m_scriptName == null) {
			throw new Exception("Cannot locate script: " + impl);
		}		
	}

	public void execute(Shell shell, Object model, 
			Console console, Map<String, String> argsMap) {
		
		shell.sourceInternalScript(m_scriptName, 
				getArgs(argsMap), model, console);
	}
	
	private String locateScript(String impl) {
		// To locate the given script, we check the JCOLIN_EXT_PATH
		// If this is defined, just use this. Otherwise, 
		// just search in the current directory...
		String jcolinExtPath = System.getenv("JCOLIN_EXT_PATH");
		if (jcolinExtPath != null) {
			for (String path : jcolinExtPath.split(":")) {
				File script = new File(path + File.separator + impl);
				if (script.exists()) {					
					return script.getAbsolutePath();
				}
			}
			
		} else {
			File script = new File(impl);
			if (script.exists()) {
				return script.getAbsolutePath();
			}			
		}
	    return null;	
	}
	
	private String[] getArgs(Map<String, String> argsMap) {
		return argsMap.values().toArray(new String[]{});
	}
}
