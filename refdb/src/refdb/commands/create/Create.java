package refdb.commands.create;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.TypeUtils;
import refdb.Model;

public class Create implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {   
        int id = ((Model)model).createReference(
                TypeUtils.getString(args, "title"));
        console.display(Integer.toString(id) + "\n");
    }
}
