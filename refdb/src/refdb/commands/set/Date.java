package refdb.commands.set;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;
import refdb.Model;
import refdb.commands.CommandUtils;

public class Date implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {
        boolean success = ((Model)model).setDate(
                CommandUtils.getRefId(args, (Model)model),
                TypeUtils.getString(args, "date"), console);
        console.display(Boolean.toString(success) + "\n");
    }
}
