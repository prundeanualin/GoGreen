package gogreenserver.repositories;

import gogreenserver.entity.AddSolarpanels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddSolarPanelsRepository extends JpaRepository<AddSolarpanels, Long> {
    List<AddSolarpanels> findAllByUserName(String userName);
}
