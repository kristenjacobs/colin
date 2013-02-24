package jcolin.commands.source;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class ScriptInterface {	
	private Shell m_shell;
	private Object m_model;
	private Console m_console;
	
    public ScriptInterface(Shell shell, Object model, Console console) {
    	m_shell = shell;
    	m_model = model;
    	m_console = console;
    }

    public String exec(String[] args) throws Exception {
    	return m_shell.execute(args, m_model, m_console);
    }
}
