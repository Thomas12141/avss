package Logic;

import Data.DataList1;
import Data.DataList2;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BootRepository{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String numberPattern = "[1-9][0-9]*";
    private static String namePattern = "[a-zA-Z[ ]*]+";

    public synchronized ArrayList<VerlieheneBoote> getAllRented() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<VerlieheneBoote> boats = convertToRentedBoats(DataList1.getAllElements());
        ArrayList<VerlieheneBoote> result = new ArrayList<>();
        for (VerlieheneBoote boat: boats) {
            if(boat.getVerliehen().equals("ja")){
                result.add(boat);
            }
        }
        return result;
    }

    public synchronized ArrayList<NichtVerlieheneBoote> getAllNotRented() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<NichtVerlieheneBoote> boats = convertToNotRentedBoats(DataList2.getAllElements());
        ArrayList<NichtVerlieheneBoote> result = new ArrayList<>();
        for (NichtVerlieheneBoote boat: boats) {
            if(boat.getVerliehen().equals("nein")){
                result.add(boat);
            }
        }
        return result;
    }
    public synchronized void addNewBootToList(String id) throws ParserConfigurationException, IOException, TransformerException, SAXException {

        if(!Pattern.compile(numberPattern).matcher(id).matches()) throw new NullPointerException("Boat object is missing required information.");

        if(idExists(id)) throw new IllegalArgumentException("ID already exists. Please choose a different ID.");

        DataList2.addNewElement(id);
    }

    public synchronized boolean idExists(String id) throws ParserConfigurationException, IOException, SAXException {

        ArrayList<NichtVerlieheneBoote> nichtVerlieheneBooteArrayList = convertToNotRentedBoats(DataList2.getAllElements());

        ArrayList<VerlieheneBoote> verlieheneBooteArrayList = convertToRentedBoats(DataList1.getAllElements());

        for (NichtVerlieheneBoote boot : nichtVerlieheneBooteArrayList) {
            if (boot.getId().equals(id)) {
                return true;
            }
        }

        for (VerlieheneBoote boot : verlieheneBooteArrayList) {
            if (boot.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isValidDateFormat(String ausleihdatumStr, String rueckgabedatumStr){
        try {
            LocalDate.parse(ausleihdatumStr, formatter);
            LocalDate.parse(rueckgabedatumStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public synchronized boolean checkAusleihdatumKleinerRueckgabedatum(LocalDate ausleihdatum, LocalDate rueckgabedatum){
        if(ausleihdatum.isAfter(rueckgabedatum)){
            return false;
        }
        return true;
    }

    public synchronized void addBootToList(String id, String ausleihdatumStr, String rueckgabedatumStr, String kundennname) throws Exception {

        if(!Pattern.compile(numberPattern).matcher(id).matches()) throw new NullPointerException("Boat object is missing required information.");

        if(idExists(id)) throw new IllegalArgumentException("ID already exists. Please choose a different ID.");

        if(!isValidDateFormat(ausleihdatumStr, rueckgabedatumStr)) throw new Exception("Invalid date format. Please use the date format 'dd/mm/yyyy'.");

        if(!Pattern.compile(namePattern).matcher(kundennname).matches()) throw new IllegalArgumentException("Name must consist only letters and blank spaces.");
        LocalDate ausleihdatum = LocalDate.parse(ausleihdatumStr, formatter);
        LocalDate rueckgabedatum = LocalDate.parse(rueckgabedatumStr, formatter);

        if(!checkAusleihdatumKleinerRueckgabedatum(ausleihdatum, rueckgabedatum)) throw new Exception("Error in date indication: 'Rueckgabedatum' < 'Ausleihdatum'.");

        DataList1.addNewElement(id, ausleihdatumStr, rueckgabedatumStr, kundennname);
    }

    public synchronized void deleteBootFromList(String id) throws ParserConfigurationException, IOException, TransformerException, SAXException, IllegalAccessException {
        ArrayList<NichtVerlieheneBoote> notRentedBoatsboats = convertToNotRentedBoats(DataList2.getAllElements());
        NichtVerlieheneBoote notRentedBoat = null;
        for (NichtVerlieheneBoote toPick:notRentedBoatsboats) {
            if(toPick.getId().equals(id)){
                notRentedBoat = toPick;
                break;
            }
        }
        if(!idExists(id)){
            throw new NullPointerException("This id does not exist.");
        }
        /*if(notRentedBoat == null){
            throw new IllegalAccessException("Rented boats cannot be deleted.");
        }*/
        DataList2.deleteNotRentedElement(id);
    }

    public synchronized void deleteRentedBootFromList(String id) throws ParserConfigurationException, IOException, TransformerException, SAXException, IllegalAccessException {
        ArrayList<VerlieheneBoote> rentedBoatsboats = convertToRentedBoats(DataList1.getAllElements());
        VerlieheneBoote rentedBoat = null;
        for (VerlieheneBoote toPick: rentedBoatsboats) {
            if(toPick.getId().equals(id)){
                rentedBoat = toPick;
                break;
            }
        }
        if(!idExists(id)){
            throw new NullPointerException("This id does not exist.");
        }
        /*if(rentedBoat == null){
            throw new IllegalAccessException("Rented boats cannot be deleted.");
        }*/
        DataList1.deleteRentedElement(id);
    }

    public void bootListeAusgeben(String id, String verliehen) throws ParserConfigurationException, IOException, SAXException {

        if(id.isEmpty() || verliehen.isEmpty()) throw new NullPointerException("Boat object is missing required information.");

        DataList1.getAllElements();
    }

    private synchronized ArrayList<NichtVerlieheneBoote> convertToNotRentedBoats(ArrayList<String> strings){

        ArrayList<NichtVerlieheneBoote> boats = new ArrayList<>();
        for (String string: strings) {
            String[] arr = string.split(";");
            if(arr.length==1) {
                NichtVerlieheneBoote boat = new NichtVerlieheneBoote(arr[0]);
                boats.add(boat);
            }
        }
        return boats;
    }

    private synchronized ArrayList<VerlieheneBoote> convertToRentedBoats(ArrayList<String> strings){

        ArrayList<VerlieheneBoote> rentedBoats = new ArrayList<>();
        for (String string: strings) {
            String[] arr = string.split(";");
            if(arr.length==4) {
                VerlieheneBoote rentedBoat = new VerlieheneBoote(arr[0], arr[1], arr[2], arr[3]);
                rentedBoats.add(rentedBoat);
            }
        }
        return rentedBoats;
    }

}