package refdb;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import jcolin.consoles.IConsole;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {
    public static final int INVALID_ID = -1;
    private int m_lastRefId;
    
    private Collection<Reference> m_references;

    public Model() {
        m_references = new ArrayList<Reference>();
        m_lastRefId = INVALID_ID;
    }

    public int createReference(String title) {
        int id = getNextId();
        m_references.add(new Reference(id, title));
        m_lastRefId = id;
        return id;
    }
    
    public boolean deleteReference(int refId, IConsole console) {
        Reference reference = getReference(refId, console);
        if (reference != null) {
            m_references.remove(reference);
            return true;
        }       
        return false;
    }
    
    public int addAuthor(int refId, String author, IConsole console) {
        Reference reference = getReference(refId, console);
        return (reference != null) ? reference.addAuthor(author) : INVALID_ID;
    }
    
    public boolean removeAuthor(int refId, int authorId, IConsole console) {
        Reference reference = getReference(refId, console);
        return (reference != null) ? reference.removeAuthor(authorId) : false;
    }
    
    public int addInfo(int refId, String info, IConsole console) {
        Reference reference = getReference(refId, console);
        return (reference != null) ? reference.addInfo(info) : INVALID_ID;
    }
    
    public boolean removeInfo(int refId, int infoId, IConsole console) {
        Reference reference = getReference(refId, console);
        return (reference != null) ? reference.removeInfo(infoId) : false;
    }
    
    public boolean setDate(int refId, String date, IConsole console) {
        Reference reference = getReference(refId, console);
        if (reference != null) {
            reference.setDate(date);
            return true;
        }
        return false;               
    }
    
    public boolean setISBN(int refId, String ISBN, IConsole console) {
        Reference reference = getReference(refId, console);
        if (reference != null) {
            reference.setISBN(ISBN);
            return true;
        }
        return false;       
    }
    
    public void listReferences(IConsole console) {
        for (Reference reference : m_references) {

            console.display("[" + reference.getId() + "] ");
            console.display("\"" + reference.getTitle() + "\"\n");
            
            if (!reference.getAuthors().isEmpty()) {
                console.display("  ");  
                for (ReferenceField field : reference.getAuthors()) {
                    console.display(field.getData() + "[" + field.getId() + "], ");                    
                }
                console.display("\n");              
            }

            for (ReferenceField field : reference.getInfos()) {
                console.display("  " + field.getData() + "\n");                 
            }
            
            if (reference.getDate() != null) {
                console.display("  Publication Date: " + reference.getDate() + "\n");
            }
            if (reference.getISBN() != null) {
                console.display("  ISBN Number: " + reference.getISBN() + "\n");
            }
            console.display("\n");
        }
    }

    /***
     * Saves the current state of the model to XML
     */
    public void save(IConsole console, String filename) {
        try {
            Document doc = XmlUtils.createDocument();
            Element refdbElement = XmlUtils.addElement(doc, doc, "refdb");

            Element references = XmlUtils.addElement(doc, refdbElement, "references");
            for (Reference reference : m_references) {

                Element referenceElement = XmlUtils.addElement(doc, references, "reference");
                XmlUtils.addAttribute(doc, referenceElement, "id", Integer.toString(reference.getId()));
                XmlUtils.addAttribute(doc, referenceElement, "title", reference.getTitle());
                if (!reference.getAuthors().isEmpty()) {
                    Element authorsElement = XmlUtils.addElement(doc, referenceElement, "authors");
                    for (ReferenceField field : reference.getAuthors()) {
                        Element authorElement = XmlUtils.addElement(doc, authorsElement, "author");                     
                        XmlUtils.addAttribute(doc, authorElement, "id", Integer.toString(field.getId()));                   
                        XmlUtils.addAttribute(doc, authorElement, "data", field.getData());                 
                    }                    
                }
                if (!reference.getInfos().isEmpty()) {
                    Element infosElement = XmlUtils.addElement(doc, referenceElement, "infos");
                    for (ReferenceField field : reference.getInfos()) {
                        Element infoElement = XmlUtils.addElement(doc, infosElement, "info");                       
                        XmlUtils.addAttribute(doc, infoElement, "id", Integer.toString(field.getId()));                 
                        XmlUtils.addAttribute(doc, infoElement, "data", field.getData());                   
                    }
                }                                
                if (reference.getDate() != null) {
                    XmlUtils.addAttribute(doc, referenceElement, "date", reference.getDate());                  
                }
                if (reference.getISBN() != null) {
                    XmlUtils.addAttribute(doc, referenceElement, "isbn", reference.getISBN());                  
                }
            }
            XmlUtils.output(doc, new FileOutputStream(new File(filename)));     
            
        } catch (Exception e) {
            console.display("Error: Unable to save system state to file: " + filename + "\n");
        }
    }

    /***
     * Restores the current state of the model from XML
    */
    public void restore(IConsole console, String filename) {        
        try {
            Document doc = XmlUtils.createDocument(new File(filename));            
            Collection<Reference> references = new ArrayList<Reference>();

            NodeList nodes = doc.getElementsByTagName("reference");
            for (int i = 0; i < nodes.getLength(); i++) {

                Node node = nodes.item(i);
                if (node instanceof Element) {
                    Element element = (Element)node;
                    String id     = element.getAttribute("id");
                    String title  = element.getAttribute("title");
                    Reference reference = new Reference(Integer.parseInt(id), title);
                    
                    NodeList authors = ((Element) node).getElementsByTagName("author");
                    for (int j = 0; j < authors.getLength(); j++) {
                        Node author = authors.item(j);
                        if (author instanceof Element) {                            
                            reference.getAuthors().add(new ReferenceField(
                                    Integer.parseInt(((Element)author).getAttribute("id")),
                                    ((Element)author).getAttribute("data")));
                        }
                    }
                    
                    NodeList infos = ((Element) node).getElementsByTagName("info");
                    for (int j = 0; j < infos.getLength(); j++) {
                        Node info = infos.item(j);
                        if (info instanceof Element) {                          
                            reference.getInfos().add(new ReferenceField(
                                    Integer.parseInt(((Element)info).getAttribute("id")),
                                    ((Element)info).getAttribute("data")));
                        }
                    }
                    
                    String date = element.getAttribute("date");
                    if (date != null) reference.setDate(date);
                    
                    String isbn = element.getAttribute("isbn");
                    if (isbn != null) reference.setISBN(isbn);
                    
                    references.add(reference);
                }
            }
            m_references = references;
            
        } catch (Exception e) {
            console.display("Error: Unable to restore system state from file: " + filename + "\n");         
        }
    }
    
    public Collection<Integer> getRefIds() {
        Collection<Integer> refIds = new ArrayList<Integer>();
        for (Reference reference : m_references) {
            refIds.add(reference.getId());
        }
        return refIds;
    }

    public int getLastRefId() {
        return m_lastRefId;
    }

    public Collection<Integer> getAuthorIds(int refId, IConsole console) {
        return getIds(getReference(refId, console).getAuthors());
    }    
    
    public Collection<Integer> getInfoIds(int refId, IConsole console) {
        return getIds(getReference(refId, console).getInfos());
    }

    public Reference getReference(int id, IConsole console) {
        for (Reference reference : m_references) {
            if (id == reference.getId()) {
                return reference;
            }
        }
        console.displayError("Invalid reference id: " + id + "\n");
        return null;
    }
    
    private Collection<Integer> getIds(Collection<ReferenceField> refFlds) {
        Collection<Integer> ids = new ArrayList<Integer>();
        for (ReferenceField refFld : refFlds) {
            ids.add(refFld.getId());
        }
        return ids;     
    }
        
    private int getNextId() {
        int maxId = -1;
        for (Reference reference : m_references) {
            maxId = Math.max(maxId, reference.getId());
        }
        return maxId + 1;
    }
 }
