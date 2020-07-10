/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.utils;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.w3c.dom.Document;

/**
 *
 * @author sherl
 */
public class DocumentUtil implements Serializable {

	public static InputStream parseDocumentToInputStream(Document document) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputFormat outputFormat = new OutputFormat(document);
		XMLSerializer xmlSerializer = new XMLSerializer(outputStream, outputFormat);
		xmlSerializer.serialize(document);
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
}
