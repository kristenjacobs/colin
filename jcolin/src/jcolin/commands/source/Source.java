package jcolin.commands.source;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

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
    		shell.sourceInternalScript(m_fileName, m_args, model, console);
    		break;
            
    	case EXTERNAL:
    		shell.sourceExternalScript(this, m_fileName);
    		break;
    	}
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
