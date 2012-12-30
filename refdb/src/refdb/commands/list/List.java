package refdb.commands.list;

import java.util.Map;

import refdb.Model;

import jcolin.Shell;
import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;

public class List implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {
		((Model)model).listReferences(console);
	}
}
