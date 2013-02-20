package jcolin.shell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.Vector;

import jcolin.ModelFactory;
import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.commands.Invalid;
import jcolin.commands.source.ScriptConsoleWriter;
import jcolin.commands.source.ScriptInterface;
import jcolin.consoles.Console;
import jcolin.consoles.Console.Context;
import jcolin.utils.StringUtil;

import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

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
    private int m_returnCode;
    private String m_toolName;
    private String m_version;
    private String m_prompt;

	public Shell(Console console, CommandFactory commandFactory, 
			ModelFactory modelFactory, String[] args, 
			String toolName, String version, String prompt) {
		
		m_toolName = toolName;
		m_version = version;
		m_prompt = prompt;
		
		try {
		    initialise(console, commandFactory, modelFactory);
	    	processCommands(args);
			
		} catch (Exception e) {
	    	console.displayError("Initialisation error detected: " + 
    	        e.getMessage() + "\n");	   
	    	System.exit(1);
		}
	}

	/**
	 * Used by the external script interface  
	 */
    public int execute() {
    	while (!isComplete()) {
    		execute(getNextCommand());
    	}
    	return m_returnCode;
    }

    /**
     * Used by the internal script interface  
     */
    public String execute(String[] args) throws Exception {
        Command command = m_commandFactory.buildCommand(
        		args, 0, new Vector<Command>(), m_console);

        if (command != null) {
            if (command.numArgs() == (args.length - 1)) {
            	execute(command);
            	return m_console.getCommandOutput();
            }
        }
        throw new Exception("invalid command");    		        	
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

    public String getToolName() {
    	return m_toolName;    	
    }

    public String getVersion() {
    	return m_version;
    }
    
	public void sourceExternalScript(Command sourceCommand, String fileName) {    	    
        try {
            BufferedReader fp = new BufferedReader(new FileReader(fileName));
            int location = getLocation(sourceCommand);
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
	                            		tokens, 0, getCommandHistory(), m_console);

	                            boolean invalidCommand = false;
	                            if (command != null) {
	                            	if (command.getFileRedirect() != null) {
	                            		invalidCommand = ((command.numArgs() + 2) != (tokens.length - 1));
	                            	} else {
	                            		invalidCommand = (command.numArgs() != (tokens.length - 1));
	                            	}
	                                if (!invalidCommand) {
	                            	    addCommand(command, location + 1);
	                            	    location++;
	                                }
	                            } else {
	                            	invalidCommand = true;
	                            }

	                            if (invalidCommand) {
	                            	String errorMessage = String.format("invalid command");
	                            	addCommand(new Invalid(commandString, errorMessage), location+1);
	                            	location++;
	                            }
	                            commandString = "";
                            }
                    	}
                    }
                }
                fp.close();
                
            } catch (IOException e) {
            	System.out.printf("Error: problem reading the file: %s", fileName);
            }
        } catch (IOException e) {
        	System.out.printf("Error: unable to open file for reading: %s", fileName);
        }
    }
        
    public void sourceInternalScript(String fileName, String[] args) {
    	Properties postProperties = new Properties();
    	Properties systemProperties = System.getProperties();    	

    	String pythonHome = System.getenv("PYTHON_HOME");
    	if (pythonHome == null) {
    		m_console.displayError("PYTHON_HOME environment variable not set");
    		return;    		
    	}
    	
    	String pythonVerbose = System.getenv("PYTHON_VERBOSE");  
    	if (pythonVerbose == null) {
    		m_console.displayError("PYTHON_VERBOSE environment variable not set");
    		return;    		
    	}

    	systemProperties.setProperty("python.home", pythonHome);
    	systemProperties.setProperty("python.verbose", pythonVerbose);

	    PythonInterpreter.initialize(systemProperties, postProperties, new String[] {""});        
        PythonInterpreter interp = getPythonInterpreter(args);

        interp.setOut(new ScriptConsoleWriter(m_console));
        interp.setErr(new ScriptConsoleWriter(m_console));
        
        interp.set(m_toolName, new ScriptInterface(this));        
        try {        	
        	m_console.setContext(Context.INTERNAL);
            interp.execfile(fileName);
        	m_console.setContext(Context.EXTERNAL);

        } catch (Exception e) {
        	m_console.display(e.toString() + "\n");
        }
    }    
    
    private void initialise(Console console, CommandFactory commandFactory, ModelFactory modelFactory) throws Exception {
	    m_model = modelFactory.createModel();
		m_console = console;
		m_commandFactory = commandFactory;
		m_state = State.RUNNING;
	    m_commands = new LinkedList<Command>();
	    m_commandHistory = new Vector<Command>();
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
    		String prompt = (m_commandHistory.size() + 1) + m_prompt;
    		m_console.displayPrompt(prompt);

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

    private void execute(Command command) {
       	if (preCommandExecute(command)) {
           	command.execute(this, m_model, m_console);
           	postCommandExecute(command);
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
	
    private void addCommand(Command command, int position) {
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

	private boolean preCommandExecute(Command command) {
		
		// The console is used to store the command output
		// in order for the output validation and the
		// internal DSL scripts to access it.
		m_console.clearCommandOutput();
		
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
	
    private PythonInterpreter getPythonInterpreter(String[] args) {
        PySystemState pySystemState = new PySystemState();        
       	PyList argv = new PyList();
        for (String arg : args) {
            argv.append(new PyString(arg));
        }
        pySystemState.argv = argv;        
        return new PythonInterpreter(null, pySystemState);    	
    }
}
