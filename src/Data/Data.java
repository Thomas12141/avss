package Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

public class Data {

    static String pattern = "dd/MM/yyyy";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    static File xmlFile = new File("myXML.xml");

    public static void addNewElement(String id, String verliehen, String ausleihdatum, String rueckhgabedatum,
                                     String kundennname) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        Element root = document.getDocumentElement();

        Element boot = document.createElement("Boot");
        boot.setAttribute("ID", id);
        boot.setIdAttribute("ID", true);
        root.appendChild(boot);

        Element verliehenElement = document.createElement("Verliehen");
        verliehenElement.appendChild(document.createTextNode(verliehen));
        boot.appendChild(verliehenElement);

        Element ausleihdatumDatumElement = document.createElement("Ausleihdatum");
        ausleihdatumDatumElement.appendChild(document.createTextNode(ausleihdatum));
        boot.appendChild(ausleihdatumDatumElement);

        Element rueckgabeDatumElement = document.createElement("Rueckgabedatum");
        rueckgabeDatumElement.appendChild(document.createTextNode(rueckhgabedatum));
        boot.appendChild(rueckgabeDatumElement);

        Element kundennameElement = document.createElement("Kundenname");
        kundennameElement.appendChild(document.createTextNode(kundennname));
        boot.appendChild(kundennameElement);


        DOMSource domSource = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(domSource, result);


    }

    public static void deleteElement(String id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        NodeList bootList = document.getElementsByTagName("Boot");

        for (int i = 0; i < bootList.getLength(); i++) {
            Element bootElement = (Element) bootList.item(i);
            String elementId = bootElement.getAttribute("ID");

            if (elementId.equals(id)) {
                bootElement.getParentNode().removeChild(bootElement);

                DOMSource domSource = new DOMSource(document);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(domSource, result);
                return;
            }
        }
    }
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        addNewElement("510", "ja", "14125", "35235", "thah");
    }
}
