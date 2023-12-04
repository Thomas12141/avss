package Logic;
public class VerlieheneBoote {
    private String id;
    private static String verliehen;
    private String ausleihdatum;
    private String rueckhgabedatum;
    private String kundennname;


    public VerlieheneBoote(String id, String ausleihdatum, String rueckhgabedatum, String kundennname) {
        this.id = id;
        this.verliehen = "ja";
        this.ausleihdatum = ausleihdatum;
        this.rueckhgabedatum = rueckhgabedatum;
        this.kundennname = kundennname;
    }

    public void setVerliehen(String verliehen) {
        this.verliehen = verliehen;
    }

    public void setAusleihdatum(String ausleihdatum) {
        this.ausleihdatum = ausleihdatum;
    }

    public void setRueckhgabedatum(String rueckhgabedatum) {
        this.rueckhgabedatum = rueckhgabedatum;
    }

    public void setKundennname(String kundennname) {
        this.kundennname = kundennname;
    }

    public String getId() {
        return id;
    }

    public static String getVerliehen() {
        return verliehen;
    }

    public String getAusleihdatum() {
        return ausleihdatum;
    }

    public String getRueckhgabedatum() {
        return rueckhgabedatum;
    }

    public String getKundennname() {
        return kundennname;
    }
    @Override
    public String toString() {
        return "Id: " + id + "\t\t" + "Kunde: " + kundennname + "\t\t" + "Verliehen am: " + ausleihdatum + "\t\t" + "bis: " + rueckhgabedatum + ".";
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((VerlieheneBoote)obj).id);
    }
}
