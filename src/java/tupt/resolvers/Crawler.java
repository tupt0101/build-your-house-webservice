/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.resolvers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import tupt.constants.DomainConstant;
import tupt.utils.DocumentUtil;

/**
 *
 * @author sherl
 */
public class Crawler implements Serializable {

    public static List<DOMResult> doCrawlFromPaginatedSite(String xmlConfigPath, String xslPath, String hrefName, String hrefValue)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException, IOException, Exception {
        
        /**
         * Parse XML file to DOM
         */
        DOMResult domResult = new DOMResult();

        InputStream xmlInputStream = new FileInputStream(xmlConfigPath);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(new StreamSource(xmlInputStream), domResult);

        Document doc = (Document) domResult.getNode();
        Node materialNode = doc.getChildNodes().item(0);
        
        /**
         * Add page number
         */
        StreamSource xslStreamSource = new StreamSource(xslPath);
        HTMLResolver htmlResolver = new HTMLResolver();
        factory.setURIResolver(htmlResolver);
        transformer = factory.newTransformer(xslStreamSource);
      
        ArrayList<DOMResult> domResults = new ArrayList<>();
        int pageNo = 1;
        System.out.println("hrefValue: " + hrefValue); 
        while (true) {
            domResult = new DOMResult();
            // Update href attribute
            String newHref = "";
            if (hrefValue.startsWith(DomainConstant.DONGTAM)) {
                newHref = hrefValue + "page/" + pageNo + "/?per_page=36";
            } else if (hrefValue.startsWith(DomainConstant.VLXD24H)) {
                newHref = hrefValue + "?pn=" + pageNo;
            }
            materialNode.getAttributes().getNamedItem(hrefName).setNodeValue(newHref);

            System.out.println("newHref: " + newHref);
            InputStream is = DocumentUtil.parseDocumentToInputStream(doc);
            StreamSource streamSource = new StreamSource(is);
            transformer.transform(streamSource, domResult);
            
            domResults.add(domResult);

            String txtMaxPage = domResult.getNode().getChildNodes().item(0).getAttributes().getNamedItem("maxPage").getNodeValue();
            int maxPage = txtMaxPage.equals("") ? 0 : Integer.parseInt(txtMaxPage);
            if (maxPage < pageNo || maxPage == 0) {
                break;
            }
            pageNo++;
        }

        return domResults;
    }

    public static DOMResult doCrawlFromSingleSite(String xmlConfigPath, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {
        DOMResult domResult = new DOMResult();

        StreamSource xslStreamSource = new StreamSource(xslPath);
        InputStream xmlInputStream = new FileInputStream(xmlConfigPath);

        TransformerFactory tf = TransformerFactory.newInstance();
        HTMLResolver hTMLResolver = new HTMLResolver();
        tf.setURIResolver(hTMLResolver);
        Transformer transformer = tf.newTransformer(xslStreamSource);

        StreamSource streamSource = new StreamSource(xmlInputStream);
        transformer.transform(streamSource, domResult);

        return domResult;
    }

    public static XPath createXPath() throws Exception {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();
        return xPath;
    }
}
