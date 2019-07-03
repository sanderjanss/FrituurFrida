package be.vdab.frida.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class VanTotPrijsForm {
    @NotNull
    @PositiveOrZero
    private BigDecimal van;
    @NotNull
    @PositiveOrZero
    @DecimalMax("10")
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
