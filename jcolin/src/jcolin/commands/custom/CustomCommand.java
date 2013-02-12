package jcolin.commands.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.consoles.IConsole;
import jcolin.shell.Shell;
import jcolin.validators.Validator;

public class CustomCommand extends Command {
    private String m_impl;
	private String m_description;
	private Executor m_executor;
	private List<Argument> m_args;
	private List<String> m_argValues;
	private List<Validator> m_outputValidators;
	private boolean m_outputDisplay;

	public CustomCommand(String[] names, String impl) throws Exception {
		super(names);
		m_impl = impl;
		if (m_impl.endsWith("py")) {
			m_executor = new ExtensionExecutor(m_impl);
			
		} else {
			m_executor = new CommandExecutor(m_impl);	
		}
		m_args = new ArrayList<Argument>();
		m_argValues = new ArrayList<String>();
		m_outputValidators = new ArrayList<Validator>();
		m_outputDisplay = true;
	}

	public CustomCommand(String[] names, String impl,
			Executor executor,
			List<Argument> args,
			List<String> argValues,
			List<Validator> outputValidators,
			boolean outoutDisplay) throws Exception {
		
		super(names);
		m_impl = impl;
		m_executor = executor;
		m_args = args;
		m_argValues = argValues;
		m_outputValidators = outputValidators;
		m_outputDisplay = outoutDisplay;
	}

    public int numArgs() {
    	return m_args.size();
    }

	public ArgType[] getArgTypes() {
		Collection<ArgType> argTypes = new ArrayList<ArgType>();
		for (Argument argument : m_args) {
		    argTypes.add(argument.getArgType());	
		}
		return argTypes.toArray(new ArgType[]{});
	}

    public String commandLine() {
    	String str = name();
    	for (String argValue : m_argValues) {
    		str += " " + argValue;
    	}
    	return str;
    }

    public String description(boolean detailed) {
    	String str = name();
    	for (Argument arg : m_args) {
    		str += " <" + arg.getName() + ">";
    	}
        return str + ": " + m_description;
    }

    public Command clone(String[] args, int index) {    	    	
    	try {
    		
    		List<String> argumentValues = extractArgumentList(args, index);
    		
			return new CustomCommand(names(), m_impl, m_executor,
					m_args, argumentValues, m_outputValidators, 
					m_outputDisplay);

		} catch (Exception ignore) {
			return null;
		}
    }

    public void execute(Shell shell, Object model, Console console) {
    	try {
    		if (validateArguments(console)) {
    			    			
    			// Determines if the output from this command should be 
    			// visible to the user on the console.
    			console.setOutputDisplay(m_outputDisplay);
    			
            	m_executor.execute(shell, model, console, getArgsMap()); 

            	console.setOutputDisplay(true);
            	
            	validateOutput(console);
    		}
    		
    	} catch (IllegalArgumentException e) {
            console.displayError("Illegal argument detected\n");    		
    	}
    }
    
    public void setDescription(String description) {
    	m_description = description;
    }
    
    public void addArgument(Argument arg) {
    	m_args.add(arg);
    }
    
    public void setOutputDisplay(boolean outputDisplay) {
    	m_outputDisplay = outputDisplay;
    }
    
    public void addOutputValidator(Validator validator) {
    	m_outputValidators.add(validator);
    }
        
    private Map<String, String> getArgsMap() {
    	Map<String, String> argsMap = new TreeMap<String, String>();
    	for (int i = 0; i < m_args.size(); ++i) {
    		argsMap.put(m_args.get(i).getName(), m_argValues.get(i));
    	}
    	return argsMap;
    }
    
    private List<String> extractArgumentList(String[] args, int index) {
    	List<String> argsList = Arrays.asList(args);
    	return argsList.subList(index + 1, index + 1 + numArgs());
    }
    
    private boolean validateArguments(IConsole console) {
    	boolean passed = true;
    	for (int i = 0; i < m_args.size(); ++i) {
    		Argument arg = m_args.get(i);
    		String argValue = m_argValues.get(i);
 
    		if (!arg.validate(argValue)) {				
				console.displayError("Argument: " + arg.getName() + 
						" is not valid for value: " + argValue + "\n");
				passed = false;
			}
		}
		return passed;    	
    }

    private boolean validateOutput(Console console) {
    	String output = console.getCommandOutput();
    	for (Validator validator : m_outputValidators) {
    		if (!validator.validate(output)) {				
				console.displayError("Validation failed for the output of this command\n");
				return false;
    		}
    	}
    	return true;
    }
}
