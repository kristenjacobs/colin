package jcolin.commands.help;

import jcolin.Shell;
import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.consoles.Console;

public class Help extends Command {
	private static final String[] COMMAND_NAMES = { "help" };
	
	private CommandFactory m_commandFactory;
	private Command m_command;
	private Command m_subCommand;

	public Help(CommandFactory commandFactory) {
		super(COMMAND_NAMES);
		m_commandFactory = commandFactory;
	}

	public Help(CommandFactory commandFactory, Command command) {
		super(COMMAND_NAMES);
		m_commandFactory = commandFactory;
		m_command = command;
	}

	public Help(Command command, Command subCommand) {
		super(COMMAND_NAMES);
		m_command = command;
		m_subCommand = subCommand;
	}
	
    public int numArgs() {
    	if ((m_command != null) && (m_subCommand != null)) {
        	return 2;
    	}
    	if (m_command != null) {
        	return 1;
    	}    	
   		return 0;
    }

	public ArgType[] getArgTypes() {
		return new Command.ArgType[] {ArgType.HELP, ArgType.HELPCOMMAND};
	}

    public String commandLine() {
    	if ((m_command != null) && (m_subCommand != null)) {
        	return name() + " " + m_command.name() + " " + m_subCommand.name();
    	}
    	if (m_command != null) {
        	return name() + " " + m_command.name();
    	}    	
   		return name();
    }

    public String description(boolean detailed) {
        StringBuffer desc = new StringBuffer();
        desc.append(name());
        desc.append(" [command|command subcommand|option]: Displays help message for the given arguments");
        desc.setLength(desc.length() - 2);
        return desc.toString();
    }

    public Command clone(String[] args, int index) {
	 	if (areArgsDone(args, index + 1)) {
			return new Help(m_commandFactory);
		}

		String arg = args[index + 1];
		// Checks for matching commands..
    	for (Command command : m_commandFactory.getCommands()) {
    		if (command.name().equals(arg)) {    			
    			if (areArgsDone(args, index + 2)) {    			    			
    			    return new Help(m_commandFactory, command);
    			}
    			
    			String subArg = args[index + 2];
    			for (Command subCommand : command.getSubCommands()) {
    				if (subCommand.name().equals(subArg)) {
    					return new Help(command, subCommand);
    				}
    			}
    		}
    	}
        // Error..
    	return null;
    }

    public void execute(Shell shell, Object model, Console console) {
    	if ((m_command != null) && (m_subCommand != null)) {
    		// Displays help for a specific command + sub-command.
    		console.display(String.format("  [-]%s %s\n", m_command.name(), 
    				m_subCommand.description(true)));

    	} else if (m_command != null) {
    		// Displays help for a specific command.
    		console.display(String.format("  [-]%s\n", m_command.description(true)));
    		
    	} else {
            // Displays help for all commands.
	    	for (Command command : m_commandFactory.getCommands()) {
	    		console.display(String.format("  [-]%s\n", command.description(false)));
	    	}
    	}
    }
}
