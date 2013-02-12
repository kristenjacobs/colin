package jcolin.commands.source;

import jcolin.shell.Shell;

public class ScriptInterface {	
	private Shell m_shell;
	
    public ScriptInterface(Shell shell) {
    	m_shell = shell;
    }

    public String exec(String[] args) throws Exception {
    	return m_shell.execute(args);
    }
}
