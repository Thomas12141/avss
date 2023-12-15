package Data;

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
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataList2 {

    static File xmlFile = new File("myXML.xml");

    public static void addNewElement(String id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        Element root = document.getDocumentElement();

        Element boot = document.createElement("NichtVerlieheneBoot");
        boot.setAttribute("ID", id);
        boot.setIdAttribute("ID", true);
        root.appendChild(boot);

        Element verliehenElement = document.createElement("Verliehen");
        verliehenElement.appendChild(document.createTextNode("nein"));
        boot.appendChild(verliehenElement);

        DOMSource domSource = new DOMSource(document);
        Source xslt = new StreamSource(new File("Style.xsl"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer(xslt);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(domSource, result);
    }

    public static void deleteNotRentedElement(String id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        NodeList bootList = document.getElementsByTagName("NichtVerlieheneBoot");

        for (int i = 0; i < bootList.getLength(); i++) {
            Element bootElement = (Element) bootList.item(i);
            String elementId = bootElement.getAttribute("ID");

            if (elementId.equals(id)) {
                bootElement.getParentNode().removeChild(bootElement);

                DOMSource domSource = new DOMSource(document);
                Source xslt = new StreamSource(new File("Style.xsl"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer(xslt);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(domSource, result);
                return;
            }
        }
    }

    public static ArrayList<String> getAllElements() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        ArrayList<String> list = new ArrayList<>();
        int lenght = nodeList.getLength();

        //ArrayList<String> verliehenList = new ArrayList<>();
        ArrayList<String> nichtVerliehenList = new ArrayList<>();
        for (int i = 0; i < lenght; i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) nodeList.item(i);
                String id = el.getAttributes().item(0).toString().substring(4, el.getAttributes().item(0).toString().length()-1);
                String verliehen = el.getElementsByTagName("Verliehen").item(0).getTextContent();
                if(verliehen.equals("nein")){
                    nichtVerliehenList.add(id);
                }
            }
        }
        list.addAll(nichtVerliehenList);
        return list;
    }
}
