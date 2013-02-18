package refdb.commands.get;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;

public class RefIds implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {	
	    
		boolean first = true;
		for (int refId : ((Model)model).getRefIds()) {
			if (!first) {
				console.display(" ");
			}
			console.display(String.format("%d", refId));
			first = false;
		}
		console.display("\n");
	}
}
