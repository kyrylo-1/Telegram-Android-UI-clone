package com.shorka.telegramclone_ui.utils;

/**
 * Created by Kyrylo Avramenko on 9/5/2018.
 */
public class StringUtils {

    public static String capitalize(String str){
        return new StringBuilder().append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
    }
}
