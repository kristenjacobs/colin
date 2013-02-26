package jcolin.commands.testcase;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;
import jcolin.testing.Testcase;

public class Run extends Command {  
    private static final String[] COMMAND_NAMES = { "run" };

    private String m_testcaseRegex;
    
    public Run() {
        super(COMMAND_NAMES);
    }

    public Run(String testcaseRegex) {
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
        return name() + "  [testcase regex]: Runs the testcases";
    }

    public Command clone(String[] args, int index) {
        if (!areArgsDone(args, index + 1)) {
            String testcaseRegex = args[index + 1];
            return new Run(testcaseRegex);
        }       
        return new Run();
    }

    public void execute(Shell shell, Object model, Console console) {
        for (Testcase testcase : shell.getTestcases()) {
            if (enabled(testcase.getName())) {
                testcase.run(shell, console);
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
