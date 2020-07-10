/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.utils;

/**
 *
 * @author sherl
 */
public class UTF8StringHelper {

    /*
     * convert from UTF-8 -> internal Java String format
     */
    public static String convertFromUTF8(String str) {
        String out;
        try {
            out = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            //   out = new String(str.getBytes("windows-1258"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    /*
     * convert from internal Java String format -> UTF-8
     */
    public static String convertToUTF8(String str) {
        String out = null;
        try {
            out = new String(str.getBytes("UTF-8"), "ISO-8859-1");
            // out = new String(str.getBytes("UTF-8"), "windows-1258");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}
