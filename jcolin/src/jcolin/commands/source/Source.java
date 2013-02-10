package jcolin.commands.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.commands.Invalid;
import jcolin.consoles.Console;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.utils.StringUtil;

public class Source extends Command {	
	private static final String[] COMMAND_NAMES = { "source" };

	private enum ScriptType { EXTERNAL, INTERNAL, }

	private CommandFactory m_commandFactory;
	private String m_toolName;
	private String m_fileName;	
	private ScriptType m_scriptType;
	private String[] m_args;
	
	public Source(CommandFactory commandFactory, String toolName) {
		super(COMMAND_NAMES);
		m_commandFactory = commandFactory;
		m_toolName = toolName;
		m_fileName = "";
		m_scriptType = ScriptType.EXTERNAL;
		m_args = new String[]{};
	}

	public Source(CommandFactory commandFactory, String toolName, 
			String fileName, ScriptType scriptType, String[] args) {
		
		super(COMMAND_NAMES);
		m_commandFactory = commandFactory;
		m_toolName = toolName;
		m_fileName = fileName;
		m_scriptType = scriptType;
		m_args = args;
	}

    public int numArgs() {    	
    	switch (m_scriptType) {
    	case INTERNAL: return m_args.length;    		
    	case EXTERNAL: return 1;    		
    	default:
            assert(false);
            return 0;
    	}    	    
    }

	public ArgType[] getArgTypes() {
		return new Command.ArgType[] {ArgType.FILE};
	}

    public String commandLine() {
    	switch (m_scriptType) {
    	case INTERNAL:
        	String commandLine = name();
        	for (String arg : m_args) {
        		commandLine += " \"" + arg + "\"";
        	}
        	return commandLine; 
            
    	case EXTERNAL:    		
    		return name() + " \"" + m_fileName + "\"";
    		
    	default:
            assert(false);
            return null;
    	}    	
    }

    public String description(boolean detailed) {
        return name() + " <file name> [args]: Sources the given script file";
    }

    public Command clone(String[] args, int index) {
		if ((index + numArgs()) >= args.length)
			return null;

    	String fileName = args[index + 1];
    	ScriptType scriptType = getScriptType(fileName);
    	
    	switch (scriptType) {
    	case INTERNAL:
    		Collection<String> scriptArgs = getScriptArgs(args, index);
            return new Source(m_commandFactory, m_toolName, fileName, 
            		scriptType, scriptArgs.toArray(new String[]{}));
            
    	case EXTERNAL:
    		return new Source(m_commandFactory, m_toolName,
    				fileName, scriptType, new String[]{});
    		
    	default: 
    		assert(false);
    		return null;
    	}
    }

    public void execute(Shell shell, Object model, Console console) {
    	switch (m_scriptType) {
    	case INTERNAL:
    		sourceInternalScript(shell, model, console);
    		break;
            
    	case EXTERNAL:
    		sourceExternalScript(shell, model, console);
    		break;
    	}
    }
    
    private void sourceExternalScript(Shell shell, Object model, IConsole console) {    	    
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
    
    private void sourceInternalScript(Shell shell, Object model, Console console) {
    	Properties postProperties = new Properties();
    	Properties systemProperties = System.getProperties();    	

    	String pythonHome = System.getenv("PYTHON_HOME");
    	if (pythonHome == null) {
    		console.displayError("PYTHON_HOME environment variable not set");
    		return;    		
    	}
    	
    	String pythonVerbose = System.getenv("PYTHON_VERBOSE");  
    	if (pythonVerbose == null) {
    		console.displayError("PYTHON_VERBOSE environment variable not set");
    		return;    		
    	}

    	systemProperties.setProperty("python.home", pythonHome);
    	systemProperties.setProperty("python.verbose", pythonVerbose);

	    PythonInterpreter.initialize(systemProperties, postProperties, new String[] {""});        
        PythonInterpreter interp = getPythonInterpreter(m_args);

        interp.setOut(new ScriptConsoleWriter(console));
        interp.setErr(new ScriptConsoleWriter(console));
        
        interp.set(m_toolName, new ScriptInterface(
        		m_commandFactory, shell, model, console));        
        try {
            interp.execfile(m_fileName);
            
        } catch (Exception e) {
        	console.display(e.toString() + "\n");
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
    
    private ScriptType getScriptType(String fileName) {
    	String extension = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
    	if (extension.equals("py")) {   
    		return ScriptType.INTERNAL;
    	} else {
    		return ScriptType.EXTERNAL;
    	}
    }
    
    private Collection<String> getScriptArgs(String[] args, int index) {  		
        Collection<String> scriptArgs = new ArrayList<String>();
	    int i = 1;
	    while (!areArgsDone(args, index + i)) {
		    scriptArgs.add(args[index + i]);
		    ++i;
	    }    	
        return scriptArgs;
    }    
}
