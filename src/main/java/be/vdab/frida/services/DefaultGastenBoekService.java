package be.vdab.frida.services;

import be.vdab.frida.domain.GastenBoekEntry;
import be.vdab.frida.repositories.GastenBoekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultGastenBoekService implements GastenBoekService {
    private final GastenBoekRepository repository;

    public DefaultGastenBoekService(GastenBoekRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public long create(GastenBoekEntry entry) {
        return repository.create(entry);
    }

    @Override
    public List<GastenBoekEntry> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void delete(long[] ids) {
        Arrays.stream(ids).forEach(id->repository.delete(id));
    }
}
