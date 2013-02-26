package refdb;

import jcolin.JColin;

public class RefDb {
    static public void main(String[] args) {
        System.exit(new JColin().start(new RefDbFactory(), args));
    }
}
