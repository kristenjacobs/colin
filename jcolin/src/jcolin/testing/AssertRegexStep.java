package jcolin.testing;

public class AssertRegexStep extends AssertStep {
	
	public AssertRegexStep(String var, String value) {
		super(var, value);
	}

	@Override
	public boolean doCompare(String value, String expected) {
		return value.matches(expected);
	}
}
