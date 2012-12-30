package jcolin.commands.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jcolin.Shell;
import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.commands.Invalid;
import jcolin.consoles.Console;
import jcolin.consoles.IConsole;
import jcolin.utils.StringUtil;

public class Source extends Command {	
	private static final String[] COMMAND_NAMES = { "source" };

	private CommandFactory m_commandFactory;
	private String m_fileName;
	
	public Source(CommandFactory commandFactory) {
		super(COMMAND_NAMES);
		m_commandFactory = commandFactory;
		m_fileName = "";
	}

	public Source(CommandFactory commandFactory, String fileName) {
		super(COMMAND_NAMES);
		m_commandFactory = commandFactory;
		m_fileName = fileName;
	}

    public int numArgs() {
    	return 1;    		
    }

	public ArgType[] getArgTypes() {
		return new Command.ArgType[] {ArgType.FILE};
	}

    public String commandLine() {
   		return name() + " \"" + m_fileName + "\"";
    }

    public String description(boolean detailed) {
        return name() + " <file name> [args]: Sources the given script file";
    }

    public Command clone(String[] args, int index) {
		if ((index + numArgs()) >= args.length)
			return null;

    	String fileName = args[index + 1];
    	return new Source(m_commandFactory, fileName);
    }

    public void execute(Shell shell, Object model, Console console) {
   		sourceScript(shell, model, console);
    }
    
    private void sourceScript(Shell shell, Object model, IConsole console) {    	    
        try {
            BufferedReader fp = new BufferedReader(new FileReader(m_fileName));
            int location = shell.getLocation(this);
            try {
                String line;
                String commandString = "";
                boolean multilineCommand = false;

                while ((line = fp.readLine()) != null) {
                    line = line.trim();
                    if (line.length() > 0) {

                        if (line.startsWith("#")) {
                        	// Ignore comment lines...

                        } else if (line.endsWith("\\")) {
                        	// Handles the line continuation character...
                        	commandString += " " + line.substring(0, line.length() - 2).trim();
                        	multilineCommand = true;

                        } else {
                            if (multilineCommand) {
                            	commandString += " " + line;
                            } else {
                            	commandString = line;
                            }
                            multilineCommand = false;

                            String[] tokens = commandString.trim().split("\\s+");
                            ArrayList<String> tokenGroups = StringUtil.groupQuotedTokens(tokens);
                            if (tokenGroups.size() > 0) {
                                tokens = tokenGroups.toArray(new String[0]);

	                            Command command = m_commandFactory.buildCommand(
	                            		tokens, 0, shell.getCommandHistory(), (Console)console);

	                            boolean invalidCommand = false;
	                            if (command != null) {
	                            	if (command.getFileRedirect() != null) {
	                            		invalidCommand = ((command.numArgs() + 2) != (tokens.length - 1));
	                            	} else {
	                            		invalidCommand = (command.numArgs() != (tokens.length - 1));
	                            	}
	                                if (!invalidCommand) {
	                            	    shell.addCommand(command, location + 1);
	                            	    location++;
	                                }
	                            } else {
	                            	invalidCommand = true;
	                            }

	                            if (invalidCommand) {
	                            	String errorMessage = String.format("invalid command");
	                            	shell.addCommand(new Invalid(commandString, errorMessage), location+1);
	                            	location++;
	                            }
	                            commandString = "";
                            }
                    	}
                    }
                }
                fp.close();
                
            } catch (IOException e) {
            	System.out.printf("Error: problem reading the file: %s", m_fileName);
            }
        } catch (IOException e) {
        	System.out.printf("Error: unable to open file for reading: %s", m_fileName);
        }
    }
}
