package be.vdab.frida.domain;

public class Adres {
    private final String straat;
    private final String huisNr;

    public Adres(String straat, String huisNr) {
        this.straat = straat;
        this.huisNr = huisNr;
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisNr() {
        return huisNr;
    }
}
