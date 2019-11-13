package gogreenserver.services;

import gogreenserver.entity.Records;
import gogreenserver.repositories.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordsService {

    private RecordsRepository recordsRepo;

    @Autowired
    public RecordsService(RecordsRepository recordsRepo) {
        this.recordsRepo = recordsRepo;
    }

    public List<Records> findAll() {
        return recordsRepo.findAll();
    }

    public Optional<Records> findById(String userName) {
        return recordsRepo.findById(userName);
    }

}
