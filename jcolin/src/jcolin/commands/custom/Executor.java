package jcolin.commands.custom;

import java.util.Map;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public interface Executor {
	public void execute(Shell shell, Object model, 
			Console console, Map<String, String> argsMap);
}
