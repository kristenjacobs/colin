package refdb.commands.add;

import java.util.Map;

import refdb.Model;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;

public class Author implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {	
	    int id = ((Model)model).addAuthor(
	    		TypeUtils.getInteger(args, "refid"),
		        TypeUtils.getString(args, "name"));
	    console.display(Integer.toString(id) + "\n");
	}
}
