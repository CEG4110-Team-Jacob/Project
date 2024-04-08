package org.example.Data.utils;

import java.util.Optional;

import org.example.Data.controllers.General;

public class Utils {
    /**
     * Default Query (Just a token)
     * 
     * @return
     */
    public static String DEFAULT_QUERY() {
        Optional<String> token = General.getToken();
        if (token.isEmpty())
            return "t=false";
        return "t=" + token.get();
    }
}
