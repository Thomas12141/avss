package Frontend;

import org.xml.sax.SAXException;
import Logic.BootRepository;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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

        JButton list1 = new JButton("list1");
        this.add(list1);

        JButton list2 = new JButton("list2");
        this.add(list2);

        this.setLayout(new FlowLayout());
        this.add(listComponents);
        this.setResizable(false);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        Dimension textFieldDimension = new Dimension(100, 25);



        JLabel blank1 = new JLabel("    ");
        blank1.setPreferredSize(new Dimension(90, 25));
        jPanel.add(blank1);

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

        JLabel blank2 = new JLabel("    ");
        blank2.setPreferredSize(new Dimension(90, 25));
        jPanel.add(blank2);

        this.add(jPanel);

        JLabel error = new JLabel("");
        this.add(error);
        aktualisieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                BootRepository bootRepository = new BootRepository();
                ArrayList<String> boats = null;
                String prefix = "<html><pre>";
                String suffix = "</pre></html>";
            }
        });
        hinzufuegen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BootRepository bootRepository = new BootRepository();
                String id = idTextField.getText();
                String ausleihDatum = ausleihdatumTextField.getText();
                String rueckgabeDatum = rueckgabedatumTextField.getText();
                String kundenName = kundennameTextField.getText();
                if(ausleihDatum.isEmpty()&&rueckgabeDatum.isEmpty()&&kundenName.isEmpty()){
                    try {
                        bootRepository.addNewBootToList(id);
                    } catch (ParserConfigurationException | IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (TransformerException ex) {
                        throw new RuntimeException(ex);
                    } catch (SAXException ex) {
                        throw new RuntimeException(ex);
                    }
                }else {
                    try {
                        bootRepository.addBootToList(id, ausleihDatum, rueckgabeDatum, kundenName);
                    } catch (Exception ex) {
                        error.setText(ex.getMessage());
                    }
                }
            }
        });

        loeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BootRepository bootRepository = new BootRepository();
                String id = idTextField.getText();
                try {
                    bootRepository.deleteBootFromList(id);
                } catch (ParserConfigurationException | TransformerException | IOException | SAXException |
                         IllegalAccessException | NullPointerException ex) {
                    error.setText(ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}
