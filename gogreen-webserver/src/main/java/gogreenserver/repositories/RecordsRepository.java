package gogreenserver.repositories;

import gogreenserver.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records, String> {
}
