package Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Data {

    static String pattern = "dd/MM/yyyy";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {

        File xmlFile = new File("myXML.xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.newDocument();

        Element boote = document.createElement("Boote");
        document.appendChild(boote);

        Element boot = document.createElement("Boot");
        boot.setAttribute("id", "123");
        boote.appendChild(boot);

        Element entliehen = document.createElement("Verliehen");
        entliehen.appendChild(document.createTextNode("ja"));
        boot.appendChild(entliehen);

        Element ausleihdatum = document.createElement("AusleihDatum");
        ausleihdatum.appendChild(document.createTextNode(simpleDateFormat.format(new Date(2023-1900, 11, 20)).toString()));
        boot.appendChild(ausleihdatum);

        Element Rueckhgabedatum = document.createElement("rueckhgabedatum");
        Rueckhgabedatum.appendChild(document.createTextNode(simpleDateFormat.format(new Date(2023-1900, 12, 31)).toString()));
        boot.appendChild(Rueckhgabedatum);

        Element Kundenname = document.createElement("kundenname");
        Kundenname.appendChild(document.createTextNode("Thomas Fidorin"));
        boot.appendChild(Kundenname);

        DOMSource domSource = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(domSource, result);
    }
}
