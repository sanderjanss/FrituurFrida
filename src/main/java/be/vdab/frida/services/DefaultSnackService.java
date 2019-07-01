package be.vdab.frida.services;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.repositories.SnackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultSnackService implements SnackService{
    private SnackRepository snackRepository;

    public DefaultSnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }


    @Override
    public Optional<Snack> findById(long id) {
        return snackRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void update(Snack snack) {
        snackRepository.update(snack);
    }

    @Override
    public List<Snack> findByBeginNaam(String letter) {
        return snackRepository.findByBeginNaam(letter);
    }

    @Override
    public List<Snack> findAll() {
        return snackRepository.findAll();
    }

    @Override
    public long findAantalSnacks() {
        return snackRepository.findAll().size();
    }

    @Override
    public List<Snack> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
        return snackRepository.findByPrijsBetween(van, tot);
    }
}
