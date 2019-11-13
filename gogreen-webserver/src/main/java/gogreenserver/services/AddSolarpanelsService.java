package gogreenserver.services;

import gogreenserver.entity.AddSolarpanels;
import gogreenserver.repositories.AddSolarPanelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddSolarpanelsService {

    private AddSolarPanelsRepository addSolarpanelsRepo;

    @Autowired
    public AddSolarpanelsService(AddSolarPanelsRepository addSolarpanelsRepo) {
        this.addSolarpanelsRepo = addSolarpanelsRepo;
    }

    public List<AddSolarpanels> findAll() {
        return addSolarpanelsRepo.findAll();
    }

    public AddSolarpanels createAddSolarpanels(AddSolarpanels addSolarpanels) {
        return addSolarpanelsRepo.save(addSolarpanels);
    }

    public List<AddSolarpanels> findAllByUserName(String userName) {
        return addSolarpanelsRepo.findAllByUserName(userName);
    }


}
