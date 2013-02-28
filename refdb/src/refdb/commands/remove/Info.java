package refdb.commands.remove;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.commands.CommandUtils;

public class Info implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {   
        boolean status = ((Model)model).removeInfo(
                CommandUtils.getRefId(args, (Model)model),
                CommandUtils.getInfoId(args, (Model)model));
        console.display(Boolean.toString(status) + "\n");
    }
}
