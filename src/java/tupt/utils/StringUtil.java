/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author sherl
 */
public class StringUtil implements Serializable {

    public static String parseInputStreamToString(InputStream inputStream) throws IOException {
        String result = null;
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        }
        
        result = stringBuilder.toString();
//        UTF8StringHelper.convertToUTF8(result);
//        System.out.println("StringUtil: " + result);
        return result;
    }

    public static InputStream parseStringToInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    public static String insert(String parentString, String childString, int row, int column) {
        int count = 0;
        String[] lines = parentString.split("\n");
        String result = "";

        for (String line : lines) {
            if (count == row) {
                line = line.substring(0, column - 1) + childString + line.substring(column - 1);
            }
            result += line + "\n";
            count++;
        }

        return result;
    }

    public static boolean isStringStartWithListString(List<String> listString, String parentString) {
        for (String string : listString) {
            if (parentString.startsWith(string)) {
                return true;
            }
        }
        return false;
    }
}
