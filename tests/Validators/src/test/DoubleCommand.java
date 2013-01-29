package test;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;

public class DoubleCommand implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {	
	    console.display("The argument value is: " + 
                TypeUtils.getDouble(args, "argument") + "\n");
	}
}

