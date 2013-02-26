package jcolin.consoles;

public interface IConsole {
    public void display(String str);
    public void displayError(String str);
    public void displayWarning(String str);
    public void displayInfo(String str);
}
