package Boot;
public class Boot {
    private String id, verliehen, ausleihdatum, rueckhgabedatum, kundennname;


    public Boot(String id, String verliehen, String ausleihdatum, String rueckhgabedatum, String kundennname) {
        this.id = id;
        this.verliehen = verliehen;
        this.ausleihdatum = ausleihdatum;
        this.rueckhgabedatum = rueckhgabedatum;
        this.kundennname = kundennname;
    }

    public Boot(String id) {
        this.id = id;
        this.verliehen = "nein";
        this.ausleihdatum = null;
        this.rueckhgabedatum = null;
        this.kundennname = null;
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

    public String getVerliehen() {
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
        if(verliehen.equals("ja"))
            return "Id: " + id + "\t" + "Kunde: " + kundennname + "\t" + "Verliehen am: " + ausleihdatum + "\t" + "bis: " + rueckhgabedatum + ".";
        else
            return "Id: " + id + "\t" + "Bereit um zu leihen.";
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((Boot)obj).id);
    }
}
