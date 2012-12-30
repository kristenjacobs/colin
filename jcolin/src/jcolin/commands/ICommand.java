package jcolin.commands;

import java.util.Map;

import jcolin.Shell;
import jcolin.consoles.IConsole;

public interface ICommand {
    public void execute(Shell shell, Object model, 
    		IConsole console, Map<String, String> args);
}
