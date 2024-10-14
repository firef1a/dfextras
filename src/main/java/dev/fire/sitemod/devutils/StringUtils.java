package dev.fire.sitemod.devutils;

import java.util.ArrayList;

public class StringUtils {
    public static String convertStringArrayListToJson(ArrayList<String> arrayList) {
        String stringBuilder = "[";

        int index = 0;
        for (String element : arrayList) {
            stringBuilder = stringBuilder + "\"" + element + "\"";
            if (index < arrayList.size()-1) {
                stringBuilder = stringBuilder + ",";
            }
            index++;
        }
        stringBuilder = stringBuilder +  "]";

        return stringBuilder;
    }
}
