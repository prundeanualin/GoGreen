package gogreenserver.repositories;

import gogreenserver.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findAllByUserName(String userName);
}
