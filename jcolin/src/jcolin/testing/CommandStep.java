package jcolin.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jcolin.consoles.TestConsole;
import jcolin.shell.Shell;

public class CommandStep implements Step {
    private String m_name;
    private String m_var;
    
    public CommandStep(String name, String var) {
        m_name = name;
        m_var = var;
    }
    
    @Override
    public void perform(Shell shell, TestConsole console,
            Map<String, String> environment, Object model) throws Exception {       
        
        String output = shell.execute(getCommandArray(), model, console);
        if (!m_var.equals("")) {
            environment.put(m_var, output);         
        }
    }
    
    @Override
    public String getFailureReason() {
        assert(false);
        return null;
    }
    
    private String[] getCommandArray() {
        Collection<String> array = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(m_name);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string without the quotes
                addCommandArg(regexMatcher.group(1), array);
                
            } else if (regexMatcher.group(2) != null) {
                // Add single-quoted string without the quotes
                addCommandArg(regexMatcher.group(2), array);
                
            } else {
                // Add unquoted word
                addCommandArg(regexMatcher.group(), array);
            }
        } 
        return array.toArray(new String[]{});
    }
    
    private void addCommandArg(String match, Collection<String> array) {
        String arg = match.trim();
        if (!arg.equals("")) {
            array.add(arg);
        }        
    }
}
