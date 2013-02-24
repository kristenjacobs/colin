package jcolin.testing;

public class AssertEqualsStep extends AssertStep {
	
	public AssertEqualsStep(String var, String value) {
		super(var, value);
	}

	@Override
	public boolean doCompare(String value, String expected) {
		return value.equals(expected);
	}
}
