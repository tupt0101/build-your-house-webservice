/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.resolvers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import tupt.constants.DomainConstant;
import tupt.refiners.XMLRefiner;
import tupt.utils.StringUtil;

/**
 *
 * @author sherl
 */
public class HTMLResolver implements Serializable, URIResolver {

    @Override
    public Source resolve(String href, String base) throws TransformerException {

        TrustManager[] trustAllCerts = new TrustManager[]{
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
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HTMLResolver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(HTMLResolver.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (href != null && StringUtil.isStringStartWithListString(DomainConstant.DOMAIN_LIST, href)) {
            try {
                URL url = new URL(href);
                URLConnection urlConnection = url.openConnection();
                urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

                StreamSource streamSource = null;

                // open connection to crawl data
                try (InputStream http = urlConnection.getInputStream()) {
                    streamSource = prepareInputStream(http);
                    return streamSource;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private int getResponseCodeOfURL(String href) throws IOException {
        URL url = new URL(href);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();
        return code;
    }

    private int getStatusCodeOfURL(String href) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(href);
        Response response = webTarget.request().get();
        return response.getStatus();
    }

    private String removeComment(String source) {
        String exp = "<!--.*?-->";
        return source.replaceAll(exp, "");
    }

    private String removeTag(String source, String tagName) {
        String exp = "<" + tagName + ".*?</" + tagName + ">";
        return source.replaceAll(exp, "");
    }

    private String getTag(String source, String tagName) {
        String result = source;
        String exp = "<" + tagName + ".*?</" + tagName + ">";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(source);

        if (matcher.find()) {
            result = matcher.group(0);
        }

        return result;
    }

    private StreamSource prepareInputStream(InputStream http) throws IOException {
        String httpString = "<html>" + StringUtil.parseInputStreamToString(http) + "/<html>";
//        httpString = UTF8StringHelper.convertToUTF8(httpString);

        // Remove enter, script and stylesheet
        httpString = httpString.replaceAll("\n", "");
        httpString = httpString.replaceAll("&nbsp", "");
        httpString = getTag(httpString, "body");
        httpString = removeTag(httpString, "script");
        httpString = removeTag(httpString, "style");
        httpString = removeComment(httpString);

        // Fix error in source
        XMLRefiner xmlRefiner = new XMLRefiner();
        httpString = xmlRefiner.doRefine(httpString);

//        System.out.println("------> " + httpString);
        http = StringUtil.parseStringToInputStream(httpString);

        return new StreamSource(http);
    }

    /**
     * NOT USE ANY MORE
     * <p>
     * Refine by adding missed close tag in {@code inputStream}
     *
     * @param inputStream the http source as a string.
     */
    /**
     * private InputStream doRefine(InputStream inputStream) throws
     * ParserConfigurationException, SAXException, IOException { boolean
     * isWellFormed = false; String httpString =
     * StringUtil.parseInputStreamToString(inputStream); httpString =
     * httpString.replaceAll("&", "&amp;"); inputStream =
     * StringUtil.parseStringToInputStream(httpString);
     *
     * SAXHtmlHandler SAXHtmlHandler = new SAXHtmlHandler(); SAXParserFactory
     * saxParserFactory = SAXParserFactory.newInstance(); SAXParser saxParser =
     * saxParserFactory.newSAXParser();
     *
     * while (!isWellFormed) { try { saxParser.parse(inputStream,
     * SAXHtmlHandler); isWellFormed = true; } catch (SAXException | IOException
     * e) { Stack<HtmlTagDTO> stack = SAXHtmlHandler.tagStack(); HtmlTagDTO
     * htmlTagDTO = stack.peek(); SAXHtmlHandler.tagStack().clear();
     *
     * String closeTag = "</" + htmlTagDTO.getTagName() + ">"; httpString =
     * StringUtil.insert(httpString, closeTag, htmlTagDTO.getLineNumber() - 1,
     * htmlTagDTO.getColumnNumber()); inputStream =
     * StringUtil.parseStringToInputStream(httpString); } } httpString =
     * httpString.replaceAll("[\n]+", "").replaceAll("[ ]+", " ");
     *
     * return StringUtil.parseStringToInputStream(httpString); }
     */
}
