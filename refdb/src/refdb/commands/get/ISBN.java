package refdb.commands.get;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.commands.CommandUtils;

public class ISBN implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {   
        
        int refId = CommandUtils.getRefId(args, (Model)model);
        String isbn = ((Model)model).getReference(refId, console).getISBN();
        if (isbn != null) {
            console.display(isbn + "\n");            
        }
    }
}
