package Frontend;

import Data.Data;
import org.xml.sax.SAXException;
import Boot.*;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class GUI extends JFrame {
    public GUI() throws ParserConfigurationException, IOException, SAXException {
        super("Verleih");
        this.setSize(1280,1024);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        DefaultListModel<String> model = new DefaultListModel<>();


        JList<String> listComponents = new JList<>();
        listComponents.setBounds(0,0,1280,800);
        listComponents.setModel(model);
        listComponents.setPreferredSize(new Dimension(1260, 800));

        this.setLayout(new FlowLayout());
        this.add(listComponents);
        this.setResizable(false);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        Dimension textFieldDimension = new Dimension(100, 25);

        JLabel idLabel = new JLabel("Id: ");
        idLabel.setPreferredSize(new Dimension(20, 25));
        jPanel.add(idLabel);

        JTextField idTextField = new JTextField();
        idTextField.setPreferredSize(textFieldDimension);
        jPanel.add(idTextField);

        JLabel ausleihdatumLabel = new JLabel("Ausleihdatum:");
        ausleihdatumLabel.setPreferredSize(new Dimension(90, 25));
        jPanel.add(ausleihdatumLabel);

        JTextField ausleihdatumTextField = new JTextField();
        ausleihdatumTextField.setPreferredSize(textFieldDimension);
        jPanel.add(ausleihdatumTextField);

        JLabel rueckgabedatumLabel = new JLabel("Rueckgabedatum:");
        rueckgabedatumLabel.setPreferredSize(new Dimension(110, 25));
        jPanel.add(rueckgabedatumLabel);

        JTextField rueckgabedatumTextField = new JTextField();
        rueckgabedatumTextField.setPreferredSize(textFieldDimension);
        jPanel.add(rueckgabedatumTextField);

        JLabel kundennameLabel = new JLabel("Kundenname:");
        kundennameLabel.setPreferredSize(new Dimension(85, 25));
        jPanel.add(kundennameLabel);

        JTextField kundennameTextField = new JTextField();
        kundennameTextField.setPreferredSize(textFieldDimension);
        jPanel.add(kundennameTextField);

        JButton hinzufuegen = new JButton("hinzufügen");
        jPanel.add(hinzufuegen);

        JButton loeschen = new JButton("löschen");
        jPanel.add(loeschen);

        JButton aktualisieren = new JButton("aktualisieren");
        jPanel.add(aktualisieren);

        this.add(jPanel);

        aktualisieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Boot> boats = null;
                try {
                    boats = Data.getAllElements();
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SAXException ex) {
                    throw new RuntimeException(ex);
                }
                for (Boot boat: boats) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("<html><pre>");
                    stringBuilder.append(boat.toString());
                    stringBuilder.append("</pre></html>");
                    model.addElement(stringBuilder.toString());
                }
            }
        });
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}
