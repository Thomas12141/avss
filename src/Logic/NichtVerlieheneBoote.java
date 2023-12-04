package Logic;
public class NichtVerlieheneBoote {
    private String id;
    private String verliehen;

    public NichtVerlieheneBoote(String id) {
        this.id = id;
        this.verliehen = "nein";
    }

    public void setVerliehen(String verliehen) {
        this.verliehen = verliehen;
    }

    public String getId() {
        return id;
    }

    public String getVerliehen() {
        return verliehen;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\t\t" + "Bereit um zu leihen.";
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((NichtVerlieheneBoote)obj).id);
    }
}
