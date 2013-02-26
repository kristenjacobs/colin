package jcolin.utils;

import java.util.ArrayList;

public class StringUtil {

    static public ArrayList<String> groupQuotedTokens(String[] tokens) {
        ArrayList<String> s = new ArrayList<String>();                

        boolean multiword = false;
        String m = "";
        for (String word : tokens){
            if (word.startsWith("\"")){
                multiword = true;
                word = word.substring(1);
            } 
            if (word.endsWith("\"")){
                multiword = false;
                word = word.substring(0, word.length()-1);
                word = m+ " " + word ;
                word = word.trim();
                m = "";
            } 
            if (multiword){
                m = m + " " + word;
            } else {
                s.add(word);
            }
        }
        return s;
    }
}