package hello;

import jcolin.JColin;
import jcolin.ModelFactory;

public class Hello {
    static public void main(String[] args) {
        System.exit(new JColin().start(new ModelFactory() {
            @Override
            public Object createModel() {
                return null;
            }            
        }, args));
    }
}
