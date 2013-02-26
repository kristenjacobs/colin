package refdb;

import jcolin.ModelFactory;

public class RefDbFactory implements ModelFactory {
    public Object createModel() {
        return new Model();
    }    
}
