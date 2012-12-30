package refdb.commands.remove;

import java.util.Map;

import refdb.Model;

import jcolin.Shell;
import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.utils.TypeUtils;

public class Info implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {	
	    ((Model)model).removeInfo(
	    		TypeUtils.getInteger(args, "refid"),
		        TypeUtils.getInteger(args, "infoid"));				
	}
}
