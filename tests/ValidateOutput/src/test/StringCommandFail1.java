package test;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;

public class StringCommandFail1 implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {	
        System.out.print("StringCommandFail1\n");
	    TypeUtils.returnString(console, "String");
	}
}

