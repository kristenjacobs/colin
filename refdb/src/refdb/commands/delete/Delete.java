package refdb.commands.delete;

import java.util.Map;

import jcolin.Shell;
import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.utils.TypeUtils;
import refdb.Model;

public class Delete implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {
	    ((Model)model).deleteReference(
	    		TypeUtils.getInteger(args, "refid"));
	}
}
