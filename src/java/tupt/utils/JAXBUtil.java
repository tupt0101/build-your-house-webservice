/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.xml.XMLConstants;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import tupt.constants.PathConstant;
import tupt.handlers.JAXBValidationHandler;

/**
 *
 * @author sherl
 */
public class JAXBUtil implements Serializable {

    public static void generateClassFromSchema(String filePath, String location) throws IOException {
        String sourcePackage = "src/java";

        SchemaCompiler schemaCompiler = XJC.createSchemaCompiler();
        schemaCompiler.setErrorListener(new ErrorListener() {
            @Override
            public void error(SAXParseException saxpe) {
                System.out.println("error: " + saxpe.getMessage());
            }

            @Override
            public void fatalError(SAXParseException saxpe) {
                System.out.println("error: " + saxpe.getMessage());
            }

            @Override
            public void warning(SAXParseException saxpe) {
                System.out.println("error: " + saxpe.getMessage());
            }

            @Override
            public void info(SAXParseException saxpe) {
                System.out.println("error:  " + saxpe.getMessage());
            }
        });
        schemaCompiler.forcePackageName(PathConstant.GENERATED_PACKAGE_NAME + location);

        File schema = new File(filePath);
        InputSource inputSource = new InputSource(schema.toURI().toString());
        schemaCompiler.parseSchema(inputSource);
        S2JJAXBModel model = schemaCompiler.bind();
        JCodeModel jCodeModel = model.generateCode(null, null);
        jCodeModel.build(new File(sourcePackage));
    }

    public static Object unmarshal(Class<?> objClass, Node node, String xsdPath) throws JAXBException, SAXException {
        JAXBContext context = JAXBContext.newInstance(objClass);
        
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdPath));
        
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new JAXBValidationHandler());
        
        Object result = unmarshaller.unmarshal(node);

        return result;
    }
}
