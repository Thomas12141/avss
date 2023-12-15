package Frontend;

import Logic.NichtVerlieheneBoote;
import Logic.VerlieheneBoote;
import org.xml.sax.SAXException;
import Logic.BootRepository;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class GUI extends JFrame {
    private JButton list1;
    private JButton list2;
    private int inWhichList;
    public GUI() throws ParserConfigurationException, IOException, SAXException {
        super("Verleih");
        this.inWhichList = 0;
        this.setSize(1024,576);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        JList<String> listComponents = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        listComponents.setModel(model);


        JScrollPane scrollPane = new JScrollPane(listComponents);
        scrollPane.setBounds(0, 0, 1024, 400);
        scrollPane.setPreferredSize(new Dimension(1004, 400));


        this.list1 = new JButton("Verliehene Boote");
        this.add(this.list1);

        this.list2 = new JButton("Verfügbare Boote");
        this.add(this.list2);

        this.setLayout(new FlowLayout());
        this.add(scrollPane);
        this.setResizable(false);

        JPanel jPanel = new JPanel();
        //jPanel.setPreferredSize(new Dimension(1024, 40));
        jPanel.setLayout(new FlowLayout());
        Dimension textFieldDimension = new Dimension(100, 25);



        JLabel blank1 = new JLabel("                              ");
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

        JLabel blank2 = new JLabel("                                 ");
        jPanel.add(blank2);

        this.add(jPanel);

        kundennameLabel.setVisible(false);
        kundennameTextField.setVisible(false);
        rueckgabedatumLabel.setVisible(false);
        rueckgabedatumTextField.setVisible(false);
        ausleihdatumLabel.setVisible(false);
        ausleihdatumTextField.setVisible(false);
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        loeschen.setVisible(false);
        hinzufuegen.setVisible(false);
        JLabel error = new JLabel("");
        error.setForeground(Color.red);
        this.add(error);
        this.list1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inWhichList = 1;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            error.setText("");
                            model.clear();
                            BootRepository bootRepository = new BootRepository();
                            ArrayList<VerlieheneBoote> list = bootRepository.getAllRented();
                            String prefix = "<html><pre>";
                            String suffix = "</pre></html>";
                            for (VerlieheneBoote string: list) {
                                model.addElement(prefix + string.toString() + suffix);
                            }
                            idLabel.setVisible(true);
                            idTextField.setVisible(true);
                            ausleihdatumLabel.setVisible(true);
                            ausleihdatumTextField.setVisible(true);
                            kundennameLabel.setVisible(true);
                            kundennameTextField.setVisible(true);
                            rueckgabedatumLabel.setVisible(true);
                            rueckgabedatumTextField.setVisible(true);
                            loeschen.setVisible(true);
                            hinzufuegen.setVisible(true);
                        } catch (ParserConfigurationException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (SAXException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        });

        this.list2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inWhichList = 2;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            error.setText("");
                            model.clear();
                            BootRepository bootRepository = new BootRepository();
                            ArrayList<NichtVerlieheneBoote> list = bootRepository.getAllNotRented();
                            String prefix = "<html><pre>";
                            String suffix = "</pre></html>";
                            for (NichtVerlieheneBoote boot: list) {
                                model.addElement(prefix + boot.toString() + suffix);
                            }
                            kundennameLabel.setVisible(false);
                            kundennameTextField.setVisible(false);
                            rueckgabedatumLabel.setVisible(false);
                            rueckgabedatumTextField.setVisible(false);
                            ausleihdatumLabel.setVisible(false);
                            ausleihdatumTextField.setVisible(false);
                            idLabel.setVisible(true);
                            idTextField.setVisible(true);
                            loeschen.setVisible(true);
                            hinzufuegen.setVisible(true);
                            blank1.setText("                                                                                  ");
                            blank2.setText("                                                                                                                      ");
                        } catch (ParserConfigurationException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (SAXException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        });

        hinzufuegen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            error.setText("");
                            BootRepository bootRepository = new BootRepository();
                            String id = idTextField.getText();
                            String ausleihDatum = ausleihdatumTextField.getText();
                            String rueckgabeDatum = rueckgabedatumTextField.getText();
                            String kundenName = kundennameTextField.getText();
                            if(ausleihDatum.isEmpty()&&rueckgabeDatum.isEmpty()&&kundenName.isEmpty()){
                                bootRepository.addNewBootToList(id);
                            }else {
                                try {
                                    bootRepository.addBootToList(id, ausleihDatum, rueckgabeDatum, kundenName);
                                } catch (Exception ex) {
                                    error.setText(ex.getMessage());
                                }
                            }
                        } catch (Exception ex) {
                            error.setText(ex.getMessage());
                        }
                    }
                });

            }
        });

        loeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            error.setText("");
                            BootRepository bootRepository = new BootRepository();
                            String id = idTextField.getText();
                            String ausleihDatum = ausleihdatumTextField.getText();
                            String rueckgabeDatum = rueckgabedatumTextField.getText();
                            String kundenName = kundennameTextField.getText();
                            if(!ausleihDatum.isEmpty() && !rueckgabeDatum.isEmpty() && !kundenName.isEmpty()){
                            bootRepository.deleteRentedBootFromList(id);
                        }else {
                            try {
                                bootRepository.deleteBootFromList(id);
                            } catch (Exception ex) {
                                error.setText(ex.getMessage());
                            }
                        }
                    } catch (Exception ex) {
                        error.setText(ex.getMessage());
                    }
                }
                });
            }
        });
    }

    public void refresh(){
        if(inWhichList == 0){
            return;
        }else if(inWhichList == 1){
            list1.doClick();
        }else {
            list2.doClick();
        }
    }
}
