package be.vdab.frida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@Component
@SessionScope
public class ZoekDeFriet implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int AANTAL_DEUREN = 7;
    private final Deur[] deuren = new Deur[AANTAL_DEUREN];
    public ZoekDeFriet() {
        reset();
    }
    public void openDeur(int index) {
        deuren[index].open();
    }
    public Deur[] getDeuren() { // public omdat opgeroepen vanuit JSP
        return deuren;
    }
    public void reset() {
        int indexMetFriet = ThreadLocalRandom.current().nextInt(AANTAL_DEUREN);
        for (int index = 0; index != AANTAL_DEUREN; index++) {
            deuren[index] = new Deur(index, index == indexMetFriet);
        }
    }
}


