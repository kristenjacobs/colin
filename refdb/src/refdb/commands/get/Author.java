package refdb.commands.get;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.Reference;
import refdb.commands.CommandUtils;

public class Author implements ICommand {
	@Override
	public void execute(Shell shell, Object model, 
			IConsole console, Map<String, String> args) {	
	    
		int refId    = CommandUtils.getRefId(args, (Model)model);
		int authorId = CommandUtils.getAuthorId(args, (Model)model);

		Reference reference = ((Model)model).getReference(refId);
		console.display(reference.getAuthor(authorId).getData() + "\n");
	}
}