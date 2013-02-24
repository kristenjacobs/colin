package jcolin.testing;

import java.util.Map;

import jcolin.consoles.TestConsole;
import jcolin.shell.Shell;

public interface Step {
	void perform(Shell shell, TestConsole console, 
		Map<String, String> environment, Object model) throws Exception;
	String getFailureReason();
}
