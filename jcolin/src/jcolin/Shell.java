package jcolin;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import jcolin.cli.CLI;
import jcolin.cli.CLIBuilder;
import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.commands.Invalid;
import jcolin.consoles.Console;
import jcolin.utils.StringUtil;

public class Shell {
	enum State {
		RUNNING,
		EXIT,
	}

	private Object m_model;
	private State m_state;
	private Console m_console;
	private CommandFactory m_commandFactory;
    private Queue<Command> m_commands;
    private Vector<Command> m_commandHistory;
    private String m_prompt;
    private CLI m_cli;
    private int m_returnCode;

	public Shell(Console console, CommandFactory commandFactory, 
			ModelFactory modelFactory, String[] args) {
		
		try {
		    initialise(console, commandFactory, modelFactory);
	    	processCommands(args);
			
		} catch (Exception e) {
	    	console.displayError("Initialisation error detected: " + 
    	        e.getMessage() + "\n");	   
	    	System.exit(1);
		}
	}

    public int execute() {
    	while (!isComplete()) {
    		Command command = getNextCommand();
           	if (preCommandExecute(command)) {
              	command.execute(this, m_model, m_console);
              	postCommandExecute(command);
           	}
    	}
    	return m_returnCode;
    }

	public void exit(int returnCode) {
    	m_state = State.EXIT;
    	m_returnCode = returnCode;
    }

    public Vector<Command> getCommandHistory() {
    	return m_commandHistory;
    }

    public void addCommand(Command command) {
    	m_commands.add(command);
    }

    public int getLocation(Command command){
    	ArrayList<Command> commands = new ArrayList<Command>();
    	commands.addAll(m_commands);
    	return commands.indexOf(command);
    }

    public void addCommand(Command command, int position) {
    	if (position == -1) {
    		addCommand(command);
    	} else {
			ArrayList<Command> commands = new ArrayList<Command>();
			commands.addAll(m_commands);
			commands.add(position, command);
			m_commands.clear();
			m_commands.addAll(commands);
    	}
    }

    public String getPrompt() {
		return m_prompt;
	}

    public String getToolName() {
    	return m_cli != null ?
    		   m_cli.getToolName() : "????";    	
    }

    public String getVersion() {
    	return m_cli != null ?
    		   m_cli.getVersion() : "????";
    }

    private void initialise(Console console, CommandFactory commandFactory, ModelFactory modelFactory) throws Exception {
	    m_model = modelFactory.createModel();
		m_console = console;
		m_commandFactory = commandFactory;
		m_state = State.RUNNING;
	    m_commands = new LinkedList<Command>();
	    m_commandHistory = new Vector<Command>();
	    
	    // Creates the custom commands from the config.xml
	    File configFile = new File("config.xml");
	    if (configFile.exists()) {
     		m_cli = new CLIBuilder(console).build(configFile);	    		
			for (Command command : m_cli.getCommands()) {
				m_commandFactory.addCustomCommand(command);
			}
	    	
	    } else {
	    	console.displayWarning("config.xml file not found\n");
	    }
	    
	    // Adds all the commands contributed by the framework.
	    m_commandFactory.addBuiltInCommands();
    }

    private void processCommands(String[] args) {
	    for (int i = 0; i < args.length; ++i) {
	    	Command command = m_commandFactory.buildCommand(args, i, m_commandHistory, m_console);
	    	if (command == null) {
	    		usage();
	    	}

	    	m_commands.offer(command);

	    	i += (command.getFileRedirect() != null) ?
	    			command.numArgs() + 2 :
	    			command.numArgs();
	    }
    }

    private Command getNextCommand() {
    	if (m_commands.size() == 0)
    		fillCommandQueueFromConsole();

		Command command = m_commands.peek();
		assert(command != null);

   	    m_commandHistory.add(command);
        m_commands.remove(command);
		return command;
    }

    private void fillCommandQueueFromConsole() {
    	while (m_commands.size() == 0) {
    		m_prompt = (m_commandHistory.size() + 1) + 
    				(m_cli != null ? m_cli.getPrompt() : ">");
    		m_console.displayPrompt(m_prompt);

    		String consoleCommand = m_console.getNextLine(m_commandHistory);

            String[] lines = consoleCommand.split("\n");
            for (String line : lines) {
                line = line.trim();

                if (line.startsWith("#") || line.length() == 0) {
                	continue; // Ignore comment and blank lines...
                }

                String[] tokens = line.trim().split("\\s+");
                ArrayList<String> tokenGroups = StringUtil.groupQuotedTokens(tokens);
                if (tokenGroups.size() > 0) {
                    tokens = tokenGroups.toArray(new String[0]);
                    Command command = m_commandFactory.buildCommand(
                    		tokens, 0, m_commandHistory, m_console);

                    if (!commandIsValid(command, tokens)) {
                    	command = new Invalid(line, "Invalid command (Try 'help'..)");
                    }
                    m_commands.offer(command);
                }
            }
    	}
    }

    private boolean commandIsValid(Command command, String[] tokens) {
    	if (command == null)
    		return false;

       	if (m_commandFactory.isPlingCommand(tokens[0]))
       		return (tokens.length == 1);

   		if (command.getFileRedirect() != null)
   			return ((command.numArgs() + 2) == (tokens.length - 1));

   		return (command.numArgs() == (tokens.length - 1));
    }

    private boolean isComplete() {
    	return (m_state == State.EXIT);
    }

	private void usage() {
		m_console.display("Avaliable console commands:\n");

      	for (Command command : m_commandFactory.getCommands()) {
      		m_console.display(String.format("  %s\n", command.description(false)));
      	}
      	
   		System.exit(1);
	}

	private boolean preCommandExecute(Command command) {
    	if (command.getFileRedirect() != null) {
    		if (!m_console.openRedirectFile(command.getFileRedirect(), command.getFileRedirectMode())) {
    			return false;
    		}
    	}
    	return true;
	}

	private void postCommandExecute(Command command) {

    	if (command.getFileRedirect() != null) {
    		m_console.closeRedirectFile();
    	}

    	if (m_console.hasEscaped()) {
    		m_commands.clear();
    	}
	}
}
