package refdb.commands.get;

import java.util.Map;

import jcolin.commands.ICommand;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import refdb.Model;
import refdb.Reference;
import refdb.commands.CommandUtils;

public class Info implements ICommand {
    @Override
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args) {   
        
        int refId  = CommandUtils.getRefId(args, (Model)model);
        int infoId = CommandUtils.getInfoId(args, (Model)model);

        Reference reference = ((Model)model).getReference(refId);
        console.display(reference.getInfo(infoId).getData() + "\n");
    }
}