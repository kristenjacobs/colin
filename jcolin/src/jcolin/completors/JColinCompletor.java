package jcolin.completors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import jline.Completor;
import jline.FileNameCompletor;
import jline.NullCompletor;
import jline.SimpleCompletor;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;

public class JColinCompletor implements Completor {
	private static final boolean DEBUG = false;

	private CommandFactory m_commandFactory;
    private JColinArgumentCompletor m_baseCompletor;
    private Map<String, Completor> m_subCompletors;

	public JColinCompletor(CommandFactory commandFactory) {
		m_commandFactory = commandFactory;
		m_subCompletors = new TreeMap<String, Completor>();
		createCommandCompletors();
		if (DEBUG) displayCommandCompletors();
	}

	@SuppressWarnings({ "rawtypes" })
	public int complete(String buffer, int cursor, List candidates) {
		List<String> tokens = tokeniseBuffer(buffer);
		if (tokens.size() > 1) {
			String lastCompleteCommand = getCompleteCommandString(tokens.iterator(),
					m_commandFactory.getCommands(), "");

			if (m_subCompletors.containsKey(lastCompleteCommand)) {
				return m_subCompletors.get(lastCompleteCommand).complete(buffer, cursor, candidates);
			} else {
			    return 0;
			}
		} else {
			return m_baseCompletor.complete(buffer, cursor, candidates);
		}
	}

	private void createCommandCompletors() {		
	  	Collection<Command> commands = m_commandFactory.getCommands();
	  	String[] commandStrings = new String[commands.size()];
	  	
	  	int index = 0;
	  	for (Command command : commands) {
            createSubCommandCompleters(command, 1, "");
	  		commandStrings[index] = command.name();
	  		++index;
	  	}
	  	m_baseCompletor = createArgCompletor(new SimpleCompletor(commandStrings), 0);
	}

	private void createSubCommandCompleters(Command command, int commandLevel, String prefix) {
        if (DEBUG) {
        	ouptutIndent(commandLevel);
        	System.out.printf("-> %s, level: %d ", command.name(), commandLevel);
        }

		Collection<Command> commands = command.getSubCommands();
	  	if (commands.size() > 0) {
	  		// Adds the completors for the sub-commands of this command.
	        if (DEBUG) System.out.printf("(NODE)\n");

     	  	String[] subCommandStrings = new String[commands.size()];
     	  	
     	  	int index = 0;
	  	  	for (Command com : commands) {
	  	  	    subCommandStrings[index] = com.name();
	  	        createSubCommandCompleters(com, commandLevel + 1, command.name());
	  	        ++index;
	  	  	}
	  	    JColinArgumentCompletor argCompletor = createArgCompletor(
  	        		new SimpleCompletor(subCommandStrings), commandLevel);
	  	    m_subCompletors.put(getCompleteCommandString(prefix, command), argCompletor);

	  	    if (DEBUG) System.out.printf("    %s\n", argCompletor.toString());

	  	} else {
	  		// Adds the completors for the options of this (leaf) sub-command.
	        if (DEBUG) System.out.printf("(LEAF) ");

	        List<HelpCommandCompletor> helpCommandCompletors = new ArrayList<HelpCommandCompletor>();
	        
  		    List<Completor> completors = new ArrayList<Completor>();
	  	    for (Command.ArgType argType : command.getArgTypes()) {
	  		    switch (argType) {
	  		    case FILE:
	  		    	if (DEBUG) System.out.printf("[file]");
	  			    completors.add(new FileNameCompletor());
	  		        break;

	  		    case HELP:
	  		    	if (DEBUG) System.out.printf("[help]");
	  			    completors.add(new HelpCompletor(m_commandFactory));
			        break;
			        			       
	  		    case HELPCOMMAND:
	  		    	if (DEBUG) System.out.printf("[helpcommand]");
	  		    	HelpCommandCompletor completor = new HelpCommandCompletor(m_commandFactory);
	  			    completors.add(completor);
	  			    helpCommandCompletors.add(completor);
			        break;
			        
	  		    case IGNORE:
	  		    	if (DEBUG) System.out.printf("[ignore]");
	  		    	completors.add(new NullCompletor());
	  		    	break;

			    default:
				    assert(false);
			        break;
	  		    }
	  	    }
	  	    if (DEBUG) System.out.printf(" (%d) ", completors.size());

    	  	if (completors.size() > 0) {
    	  		JColinArgumentCompletor argCompletor = createArgCompletor(completors, commandLevel);
	            m_subCompletors.put(getCompleteCommandString(prefix, command), argCompletor);

	            for (HelpCommandCompletor helpCommandCompletor : helpCommandCompletors) {
	            	helpCommandCompletor.setParent(argCompletor);
	            }	            
		  	    if (DEBUG) System.out.printf(" %s", argCompletor.toString());
    	  	}
	  	    if (DEBUG) System.out.printf("\n");
	  	}
	}

	private JColinArgumentCompletor createArgCompletor(Completor subCompletor, int commandLevel) {
		List<Completor> subCompletors = new ArrayList<Completor>();
		subCompletors.add(subCompletor);
		return createArgCompletor(subCompletors, commandLevel);
	}

	private JColinArgumentCompletor createArgCompletor(List<Completor> subCompletors, int commandLevel) {
		addNullCompletors(subCompletors, commandLevel);
    	subCompletors.add(new NullCompletor());
	    JColinArgumentCompletor completor = new JColinArgumentCompletor(subCompletors);
	    return completor;
	}

	private void addNullCompletors(List<Completor> completors, int commandLevel) {
		// This adds the required amount of null pointers to the start of the
		// given completors list. This is too account for the fact that the arguments
		// to this command will not start at zero, due to the command nesting.
	    int numNullCommands = 0;
		while (numNullCommands < commandLevel) {
			completors.add(numNullCommands, new NullCompletor());
			++numNullCommands;
		}
    }

	private List<String> tokeniseBuffer(String buffer) {
		  StringTokenizer st = new StringTokenizer(buffer);
		  List<String> tokens = new ArrayList<String>();
		  while (st.hasMoreTokens ()) {
		    tokens.add(st.nextToken());
		  }
		  if (buffer.endsWith(" ")) {
			  tokens.add("");
		  }
		  return tokens;
	}

	private String getCompleteCommandString(Iterator<String> iter,
			Collection<Command> commands, String lastCommandName) {

		if (iter.hasNext()) {
	        String token = iter.next();

		    for (Command command : commands) {
			    if (command.name().equals(token)) {
			    	String dash = (lastCommandName.length() > 0) ? "-" : "";
			        return lastCommandName + dash + getCompleteCommandString(iter, command.getSubCommands(), token);
			    }
		    }
		}
	    return lastCommandName;
	}

	private String getCompleteCommandString(String prefix, Command command) {
		String dash = prefix.length() > 0 ? "-" : "";
		return prefix + dash + command.name();
	}

	private void ouptutIndent(int level) {
		int i = level;
		while (i > 1) {
			System.out.printf("    ");
			--i;
		}
	}

	private void displayCommandCompletors() {
		System.out.printf("baseCompletor: %s\n", m_baseCompletor.toString());
		for (String key : m_subCompletors.keySet()) {
			System.out.printf("  %s %s\n", key, m_subCompletors.get(key).toString());
		}
	}
}
