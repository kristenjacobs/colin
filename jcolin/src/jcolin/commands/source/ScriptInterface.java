package jcolin.commands.source;

import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class ScriptInterface {
	
	private CommandFactory m_commandFactory;
	private Shell m_shell;
	private Object m_model;
	private Console m_console;
	
    public ScriptInterface(CommandFactory commandFactory, 
    		 Shell shell, Object model, Console console) {
    	
    	m_commandFactory = commandFactory;
    	m_shell = shell;
		m_model = model;
		m_console = console;
    }

    public String exec(String[] args) throws Exception {
        Command command = m_commandFactory.buildCommand(
        		args, 0, new Vector<Command>(), m_console);

        if (command != null) {
            if (command.numArgs() == (args.length - 1)) {
            	command.execute(m_shell, m_model, m_console);        
            	return m_console.getCommandOutput();
            }
        }
        throw new Exception("invalid command");    		    
    }
}
