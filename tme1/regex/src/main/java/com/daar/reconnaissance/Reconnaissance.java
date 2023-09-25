package com.daar.reconnaissance;

import com.daar.automate.IAutomate;
import com.daar.automate.NoSuchTransition;

public class Reconnaissance {
    
    /**
     * Prend un texte et une automate decrivant un langage
     * et verifie si le texte appartient au langage.
     * @throws NoSuchTransition
     */
    public static boolean match(String text, IAutomate automate) throws NoSuchTransition{
        int i = 0;
        int n = text.length();
        while (i < n) {
            String subtrs = text.substring(i);  
            if (automate.match(subtrs)) {
                return true;
            }
            i++;
        }
        return false;
    }

}