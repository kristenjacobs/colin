package refdb.commands.add;

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
	    int id = ((Model)model).addInfo(
	    		TypeUtils.getInteger(args, "refid"),
		        TypeUtils.getString(args, "info"));
	    console.display(Integer.toString(id) + "\n");
	}
}
