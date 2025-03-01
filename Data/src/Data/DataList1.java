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

public class DataList1 {

    static File xmlFile = new File("myXML.xml");


    public static void addNewElement(String id, String ausleihdatum, String rueckhgabedatum,
                                     String kundennname) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        Element root = document.getDocumentElement();

        Element boot = document.createElement("VerlieheneBoote");
        boot.setAttribute("ID", id);
        boot.setIdAttribute("ID", true);
        root.appendChild(boot);

        Element verliehenElement = document.createElement("Verliehen");
        verliehenElement.appendChild(document.createTextNode("ja"));
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
        Source xslt = new StreamSource(new File("Style.xsl"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer(xslt);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(domSource, result);


    }

    public static void deleteRentedElement(String id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        NodeList bootList = document.getElementsByTagName("VerlieheneBoote");

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

        ArrayList<String> verliehenList = new ArrayList<>();
        for (int i = 0; i < lenght; i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) nodeList.item(i);
                String id = el.getAttributes().item(0).toString().substring(4, el.getAttributes().item(0).toString().length()-1);
                String verliehen = el.getElementsByTagName("Verliehen").item(0).getTextContent();
                if(verliehen.equals("ja")){
                    String ausleihdatum = el.getElementsByTagName("Ausleihdatum").item(0).getTextContent();
                    String rueckgabedatum = el.getElementsByTagName("Rueckgabedatum").item(0).getTextContent();
                    String kundenname = el.getElementsByTagName("Kundenname").item(0).getTextContent();
                    verliehenList.add(id + ";" + ausleihdatum + ";"+ rueckgabedatum + ";" + kundenname);
                }
            }
        }
        list.addAll(verliehenList);
        return list;
    }
}
