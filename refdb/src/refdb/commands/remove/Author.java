package refdb.commands.remove;

import java.util.Map;

import refdb.Model;
import refdb.commands.CommandUtils;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;

public class Author implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {
        ((Model)model).removeAuthor(
                CommandUtils.getRefId(args, (Model)model),
                TypeUtils.getInteger(args, "authorid"));                
    }
}
