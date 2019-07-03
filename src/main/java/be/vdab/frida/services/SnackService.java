package be.vdab.frida.services;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.repositories.SnackRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SnackService {
    Optional<Snack> findById(long id);
    void update(Snack snack);
    List<Snack> findByBeginNaam(String letter);
    List<Snack> findAll();
    long findAantalSnacks();
    List<Snack> findByPrijsBetween(BigDecimal van, BigDecimal tot);
    long create(Snack snack);
}
