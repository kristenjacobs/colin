package refdb;

import java.util.ArrayList;
import java.util.Collection;

public class Reference {
	
	private int m_id;
    private String m_title;
    private Collection<ReferenceField> m_authors;
    private Collection<ReferenceField> m_infos;
    private String m_date;
    private String m_isbn;
	    
    public Reference(int id, String title) {
    	m_id = id;
    	m_title = title;
    	m_authors = new ArrayList<ReferenceField>();
    	m_infos = new ArrayList<ReferenceField>();
    }
    
    public int getId() {
    	return m_id;
    }

    public String getTitle() {
    	return m_title;
    }
    
    public Collection<ReferenceField> getAuthors() {
    	return  m_authors;
    }

    public Collection<ReferenceField> getInfos() {
    	return  m_infos;
    }

    public String getDate() {
    	return m_date;
    }

    public String getISBN() {
    	return m_isbn;
    }
    
    int addAuthor(String author) {
    	return addField(m_authors, author);
    }
    
    int addInfo(String info) {
    	return addField(m_infos, info);
    }

    boolean removeAuthor(int id) {
    	return removeField(m_authors, id);    	
    }

    boolean removeInfo(int id) {
    	return removeField(m_infos, id);    	    	
    }

    void setDate(String date) {
    	m_date = date;
    }
    
    void setISBN(String isbn) {
    	m_isbn = isbn;
    }    

    public String toString() {
    	return "[" + m_id + "], Title: " + m_title;
    }

    private int addField(Collection<ReferenceField> fields, String data) {
    	int id = getNextId(fields);
    	fields.add(new ReferenceField(id, data));
    	return id;    	
    }
    
    private boolean removeField(Collection<ReferenceField> fields, int id) {
    	for (ReferenceField field : fields) {
    		if (field.getId() == id) {
    			fields.remove(field);
    			return true;
    		}
    	}
    	return false;
    }
    
    private int getNextId(Collection<ReferenceField> fields) {
    	int maxId = -1;
    	for (ReferenceField field : fields) {
    		maxId = Math.max(maxId, field.getId());
    	}
    	return maxId + 1;
    }
}
