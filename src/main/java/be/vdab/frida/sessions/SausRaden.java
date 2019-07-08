package be.vdab.frida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.stream.IntStream;

@Component
@SessionScope
public class SausRaden implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_BEURTEN = 10;
    private String saus;
    private StringBuilder puntjes;
    private int beurten;

    public void reset(String saus) {
        this.saus = saus;
        puntjes = new StringBuilder(saus.length());
        IntStream.rangeClosed(1, saus.length()).forEach(teller -> puntjes.append('.'));
        beurten = 0;
    }

    public void doeGok(char letter) {
        int letterIndex = saus.indexOf(letter);
        if (letterIndex == -1) {
            beurten++;
        } else {
            do {
                puntjes.setCharAt(letterIndex, letter);
                letterIndex = saus.indexOf(letter, letterIndex + 1);
            } while (letterIndex != -1);
        }
    }
    public String getPuntjes() {
        return puntjes.toString();
    }
    public String getSaus() {
        return saus;
    }
    public int getBeurten() {
        return beurten;
    }
    public boolean isGewonnen() {
        return puntjes.indexOf(".") == -1;
    }
    public boolean isVerloren() {
        return beurten == MAX_BEURTEN;
    }
}
