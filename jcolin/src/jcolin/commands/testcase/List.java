package jcolin.commands.testcase;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;
import jcolin.testing.Testcase;

public class List extends Command { 
    private static final String[] COMMAND_NAMES = { "list" };
    
    private String m_testcaseRegex;
    
    public List() {
        super(COMMAND_NAMES);
        m_testcaseRegex = null;
    }

    public List(String testcaseRegex) {
        super(COMMAND_NAMES);
        m_testcaseRegex = testcaseRegex;
    }

    public int numArgs() {
        return hasRegex() ? 1 : 0;
    }

    public String commandLine() {
        return hasRegex() ? name() + " " + m_testcaseRegex : name();            
    }

    public String description(boolean detailed) {
        return name() + " [testcase regex]: Lists the testcases";
    }

    public Command clone(String[] args, int index) {        
        if (!areArgsDone(args, index + 1)) {
            String testcaseRegex = args[index + 1];
            return new List(testcaseRegex);
        }       
        return new List();
    }

    public void execute(Shell shell, Object model, Console console) {
        for (Testcase testcase : shell.getTestcases()) {
            if (enabled(testcase.getName())) {
                console.display(
                        testcase.getName() + ": " + 
                        testcase.getDescription() + "\n");              
            }           
        }
    }
    
    private boolean hasRegex() {
        return m_testcaseRegex != null;
    }
    
    private boolean enabled(String testcaseName) {
        if (m_testcaseRegex != null) {
            return testcaseName.matches(m_testcaseRegex);
        }
        return true;
    }
}

