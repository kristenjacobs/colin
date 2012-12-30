package jcolin.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import jcolin.cli.CLI;
import jcolin.commands.Command.RedirectMode;
import jcolin.commands.echo.Echo;
import jcolin.commands.exit.Exit;
import jcolin.commands.help.Help;
import jcolin.commands.history.History;
import jcolin.commands.source.Source;
import jcolin.commands.testcase.TestCase;
import jcolin.commands.version.Version;
import jcolin.consoles.Console;

public class CommandFactory {
	Collection<Command> m_commands;

	public CommandFactory() {
		m_commands = new ArrayList<Command>();
	}
	
	public void initialise(CLI cli) {
		// Adds the custom commands..
	    for (Command command : cli.getCommands()) {
	    	m_commands.add(command);
		}	
	    // Adds all the commands contributed by the framework.
		m_commands.add(new Echo());
		m_commands.add(new Exit());
		m_commands.add(new Help(this));
		m_commands.add(new History());
		m_commands.add(new Source(this));
		m_commands.add(new TestCase());
		m_commands.add(new Version());		
	}
	
	public Collection<Command> getCommands() {
		return m_commands;
	}
		
    public Command buildCommand(String[] args, int index,
    		Vector<Command> commandHistory, Console console) {
    	String arg = args[index];
    	    	
        if (isPlingCommand(arg)) {
        	Command command = getPlingCommand(arg, commandHistory);
        	if (command != null) {
				return command;
			}
        }

    	arg = stripLeadingDash(arg);
    	for (Command command : m_commands) {
    		if (command.matches(arg)) {
    			Command cloned = command.clone(args, index);
    			if (cloned != null) {
        			if (!processFileRedirect(cloned, args, index)) {
        				return null;
        			}
    			}
    			return cloned;
    		}
    	}
    	return null;
    }

    private String stripLeadingDash(String arg) {
    	String newArg = arg;
    	if (arg.charAt(0) == '-')
    		newArg = arg.substring(1);

    	return newArg;
    }

    public boolean isPlingCommand(String arg) {
        if ((arg.charAt(0) == '!'))
        	return true;

        return false;
    }

    private Command getPlingCommand(String arg, Vector<Command> commandHistory) {
    	arg = arg.substring(1);

        int commandIndex;
        try {
        	// Need to subtract 1 since command 1 is in array position 0
        	commandIndex = Integer.parseInt(arg) - 1;
        }
        catch (NumberFormatException e) {
            return null;
        }

        if ((commandIndex < 0) || (commandIndex >= commandHistory.size()))
  		    return null;

        return commandHistory.get(commandIndex);
    }

    private boolean processFileRedirect(Command command, String[] args, int index) {
    	int nextIndex = index + command.numArgs() + 1;
    	if (nextIndex < args.length) {
    	    if (args[nextIndex].equals(Command.REDIRECT_OVERWITE) ||
    	    	args[nextIndex].equals(Command.REDIRECT_APPEND)) {

    	    	// Check that the filename is specified..
    	    	if ((nextIndex + 1) >= args.length)
    	    		return false;

    	    	command.setFileRedirect(args[nextIndex + 1]);
    	    	if (args[nextIndex].equals(">")) {
    	    		command.setFileRedirectMode(RedirectMode.OVERWRITE);
    	    	} else {
    	    		command.setFileRedirectMode(RedirectMode.APPEND);
    	    	}
    	    }
    	}
    	return true;
    }
}
