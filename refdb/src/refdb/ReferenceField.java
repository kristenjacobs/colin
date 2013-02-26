package refdb;

public class ReferenceField {
    private int m_id;
    private String m_data;
    
    public ReferenceField(int id, String data) {
        m_id = id;
        m_data = data;
    }
    
    public int getId() {
        return m_id;
    }
    
    public String getData() {
        return m_data;
    }
}
