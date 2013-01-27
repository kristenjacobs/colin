package test;

import jcolin.JColin;
import jcolin.ModelFactory;

public class Test {
	static public void main(String[] args) {
        System.exit(new JColin().start(new ModelFactory() {			
			@Override
			public Object createModel() {
				return null;
			}
		}, args));
	}
}
