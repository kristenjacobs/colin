package refdb.commands.set;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;
import refdb.Model;

public class ISBN implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {
	    boolean success = ((Model)model).setISBN(
	    		TypeUtils.getInteger(args, "refid"),
		        TypeUtils.getString(args, "number"));		
	    console.display(Boolean.toString(success) + "\n");
	}
}
