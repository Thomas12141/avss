package Logic;

import Data.Data;
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
        ArrayList<Boot> allBoote = convertToBoats(Data.getAllElements());

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

    public void addBootToList(String id, String ausleihdatumStr, String rueckgabedatumStr, String kundennname) throws Exception {

        if(id.isEmpty()) throw new NullPointerException("Boat object is missing required information.");

        if(idExists(id)) throw new IllegalArgumentException("ID already exists. Please choose a different ID.");

        if(!isValidDateFormat(ausleihdatumStr, rueckgabedatumStr)) throw new Exception("Invalid date format. Please use the date format 'dd/mm/yyyy'.");

        LocalDate ausleihdatum = LocalDate.parse(ausleihdatumStr, formatter);
        LocalDate rueckgabedatum = LocalDate.parse(rueckgabedatumStr, formatter);

        if(!checkAusleihdatumKleinerRueckgabedatum(ausleihdatum, rueckgabedatum)) throw new Exception("Error in date indication: 'Rueckgabedatum' < 'Ausleihdatum'.");

        Data.addNewElement(id, ausleihdatumStr, rueckgabedatumStr, kundennname);
    }

    public void deleteBootFromList(String id) throws ParserConfigurationException, IOException, TransformerException, SAXException, IllegalAccessException {
        ArrayList<Boot> boats = convertToBoats(Data.getAllElements());
        Boot boat = null;
        for (Boot toPick:boats) {
            if(toPick.getId().equals(id)){
                boat = toPick;
                break;
            }
        }
        if(boat==null){
            throw new NullPointerException("This id dont exist.");
        }
        if(boat.getVerliehen().equals("ja")){
            throw new IllegalAccessException("Rented boats cannot be deleted.");
        }
        Data.deleteElement(id);
    }

    public void bootListeAusgeben(String id, String verliehen) throws ParserConfigurationException, IOException, SAXException {

        if(id.isEmpty() || verliehen.isEmpty()) throw new NullPointerException("Boat object is missing required information.");

        Data.getAllElements();
    }

    private ArrayList<Boot> convertToBoats(ArrayList<String> strings){
        ArrayList<Boot> boats = new ArrayList<>();
        for (String string: strings) {
            String[] arr = string.split(";");
            if(arr.length==1){
                Boot boat = new Boot(arr[0]);
                boats.add(boat);
            }else {
                Boot boat = new Boot(arr[0], arr[1], arr[2], arr[3]);
            }
        }
        return boats;
    }
}