package refdb.commands.remove;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.commands.CommandUtils;

public class Author implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {
        boolean status = ((Model)model).removeAuthor(
                CommandUtils.getRefId(args, (Model)model),
                CommandUtils.getAuthorId(args, (Model)model, console), 
                console);
        console.display(Boolean.toString(status) + "\n");       
    }
}
