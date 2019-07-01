package be.vdab.frida.forms;

import java.math.BigDecimal;

public class VanTotPrijsForm {
    private BigDecimal van;
    private BigDecimal tot;

    public VanTotPrijsForm(BigDecimal van, BigDecimal tot) {
        this.van = van;
        this.tot = tot;
    }

    public BigDecimal getVan() {
        return van;
    }

    public BigDecimal getTot() {
        return tot;
    }
}
