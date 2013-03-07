package jcolin.commands;

import java.util.Map;

import jcolin.consoles.IConsole;
import jcolin.shell.Shell;

/** 
 * Interface that custom commands need to implement.
 */
public interface ICommand {
    public void execute(Shell shell, Object model, 
            IConsole console, Map<String, String> args);
}
