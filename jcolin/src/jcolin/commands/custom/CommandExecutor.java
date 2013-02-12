package jcolin.commands.custom;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class CommandExecutor implements Executor {
	private ICommand m_command;
	
	public CommandExecutor(String impl) throws Exception {
		m_command = loadCommandClass(impl, ICommand.class);
	}

	public void execute(Shell shell, Object model, 
			Console console, Map<String, String> argsMap) {
		
		m_command.execute(shell, model, console, argsMap); 
	}
	
    private <T> T loadCommandClass(final String className, final Class<T> type) 
    		throws Exception {
    	
        return type.cast(Class.forName(className).newInstance());
    }
}
