package com.sikepvp.mafia.utility;

public class API {

    public static boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
