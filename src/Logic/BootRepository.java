package Logic;

import Data.Data;
import Boot.Boot;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class BootRepository{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void addNewBootToList(String id) throws ParserConfigurationException, IOException, TransformerException, SAXException {

        if(id == null) throw new NullPointerException("Boat object is missing required information.");

        if(idExists(id)) throw new IllegalArgumentException("ID already exists. Please choose a different ID.");

        Data.addNewElement(id);
    }

    public boolean idExists(String id) throws ParserConfigurationException, IOException, SAXException {

        Boot bootToFind = new Boot(id);
        ArrayList<Boot> allBoote = Data.getAllElements();

        for (Boot boot : allBoote) {
            if (boot.equals(bootToFind)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidDateFormat(String ausleihdatumStr, String rueckgabedatumStr){
        try {
            LocalDate.parse(ausleihdatumStr, formatter);
            LocalDate.parse(rueckgabedatumStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;           //Format ist inkorrekt
        }
    }

    public boolean checkAusleihdatumKleinerRueckgabedatum(LocalDate ausleihdatum, LocalDate rueckgabedatum){
        if(ausleihdatum.isAfter(rueckgabedatum)){
            return false;
        }
        return true;
    }

    public void addBootToList(String id, String verliehen, String ausleihdatumStr, String rueckgabedatumStr, String kundennname) throws Exception {

        if(id == null || verliehen == null) throw new NullPointerException("Boat object is missing required information.");

        if(idExists(id)) throw new IllegalArgumentException("ID already exists. Please choose a different ID.");

        if(!isValidDateFormat(ausleihdatumStr, rueckgabedatumStr)) throw new Exception("Invalid date format. Please use the date format 'dd/mm/yyyy'.");

        LocalDate ausleihdatum = LocalDate.parse(ausleihdatumStr, formatter);
        LocalDate rueckgabedatum = LocalDate.parse(rueckgabedatumStr, formatter);

        if(!checkAusleihdatumKleinerRueckgabedatum(ausleihdatum, rueckgabedatum)) throw new Exception("Error in date indication: 'Rueckgabedatum' < 'Ausleihdatum'.");

        Data.addNewElement(id, verliehen, ausleihdatumStr, rueckgabedatumStr, kundennname);
    }

    public void deleteBootFromList(String id) throws ParserConfigurationException, IOException, TransformerException, SAXException {

        Data.deleteElement(id);
    }

    public void bootListeAusgeben(String id, String verliehen) throws ParserConfigurationException, IOException, SAXException {

        if(id == null || verliehen == null) throw new NullPointerException("Boat object is missing required information.");

        Data.getAllElements();
    }
}