package Data;

import Boot.Boot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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

    public static ArrayList<Boot> getAllElements() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        ArrayList<Boot> list = new ArrayList<>();
        int lenght = nodeList.getLength();
        for (int i = 0; i < lenght; i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) nodeList.item(i);
                System.out.println(el);
                Boot boot;
            }
        }
        return list;
    }
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        //addNewElement("510", "ja", "14125", "35235", "thah");
        ArrayList list = getAllElements();
    }
}
