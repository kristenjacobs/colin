package jcolin.testing;

import java.util.Map;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public interface Step {
	boolean perform(Shell shell, Console console, 
		Map<String, String> environment, Object model) throws Exception;
	String getFailureReason();
}
