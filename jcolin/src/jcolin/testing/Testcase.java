package jcolin.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jcolin.consoles.Console;
import jcolin.consoles.TestConsole;
import jcolin.shell.Shell;

public class Testcase {

	private String m_name;
	private String m_description;
	private Collection<Step> m_steps;
	
	public Testcase(String name, String description) {
		m_name = name;
		m_description = description;
		m_steps = new ArrayList<Step>();
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getDescription() {
		return m_description;
	}
	
	public void addStep(Step step) {
		m_steps.add(step);
	}
	
	public void run(Shell shell, Console console) {
		console.display("Running testcase: " + m_name);    			
		
		// Creates a new console with which to run this testcase with..
		TestConsole testConsole = new TestConsole();
		
		// Create a new model class with which to run this testcase with...
		Object model = shell.createModel();
		
		// Create a new environment within which to run this testcase.
		Map<String, String> environment = new HashMap<String, String>();
		
		try {
			for (Step step : m_steps) {
				step.perform(shell, testConsole, environment, model);
			}
			console.display(" ... Passed\n");    			

		} catch (Exception ex) {
			console.display(" ***** FAILED *****\n");
			console.display("    [" + ex.getMessage() + "]\n");    						
		}
	}
}
