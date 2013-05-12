package refdb.commands.get;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.commands.CommandUtils;

public class Date implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {   
        
        int refId = CommandUtils.getRefId(args, (Model)model);
        String date = ((Model)model).getReference(refId, console).getDate();
        if (date != null) {
            console.display(date + "\n");            
        }
    }
}
