package gogreenserver.repositories;

import gogreenserver.entity.InsertHistoryCo2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsertHistoryCo2Repository extends JpaRepository<InsertHistoryCo2, Long> {

    /**
     * Finds all users by username.<br>
     * <b>DO NOT CHANGE THE METHOD NAME</b><br>
     * Spring user this method name to construct the corresponding query.
     * Make a single mistake and the whole program crashes.
     */
    List<InsertHistoryCo2> findByUserName(String username);


    List<InsertHistoryCo2> findByUserNameOrderByInsertDateDesc(String username);
}
