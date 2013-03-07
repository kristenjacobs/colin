package refdb.commands.delete;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.commands.CommandUtils;

public class Delete implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {
        ((Model)model).deleteReference(
                CommandUtils.getRefId(args, (Model)model), console);
    }
}
