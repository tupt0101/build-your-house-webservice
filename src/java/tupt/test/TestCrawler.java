/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.stream.StreamSource;
import tupt.refiners.XMLRefiner;
import tupt.utils.StringUtil;

/**
 *
 * @author sherl
 */
public class TestCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        
        String href = "https://dongtamshop.com/danh-muc/gach-bong/page/1/?per_page=36";      
        try {
            URLConnection urlConnection = new URL(href).openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            StreamSource streamSource;
            // open connection to crawl data
            try (InputStream http = urlConnection.getInputStream()) {
                streamSource = prepareInputStream(http);
//                return streamSource;
            }

        } catch (Exception e) {
            System.out.println("error");
        }
    }

    private static String removeComment(String source) {
        String exp = "<!--.*?-->";
        return source.replaceAll(exp, "");
    }

    private static String removeTag(String source, String tagName) {
        String exp = "<" + tagName + ".*?</" + tagName + ">";
        return source.replaceAll(exp, "");
    }

    private static String getTag(String source, String tagName) {
        String result = source;
        String exp = "<" + tagName + ".*?</" + tagName + ">";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(source);

        if (matcher.find()) {
            result = matcher.group(0);
        }

        return result;
    }

    private static StreamSource prepareInputStream(InputStream http) throws IOException {
        String httpString = "<html>" + StringUtil.parseInputStreamToString(http) + "/<html>";

        // Remove enter, script and stylesheet
        httpString = httpString.replaceAll("\n", "");
        httpString = httpString.replaceAll("&nbsp", "");
        httpString = getTag(httpString, "body");
        httpString = removeTag(httpString, "script");
        httpString = removeTag(httpString, "noscript");
        httpString = removeTag(httpString, "style");
        httpString = removeComment(httpString);

        // Fix error in source
        XMLRefiner xmlRefiner = new XMLRefiner();
        httpString = xmlRefiner.doRefine(httpString);

        System.out.println("-------->" + httpString);

        http = StringUtil.parseStringToInputStream(httpString);

        return new StreamSource(http);
    }
    
    

}
