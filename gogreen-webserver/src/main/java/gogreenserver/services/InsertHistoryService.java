package gogreenserver.services;

import gogreenserver.entity.InsertHistory;
import gogreenserver.entity.InsertHistoryCo2;
import gogreenserver.repositories.InsertHistoryCo2Repository;
import gogreenserver.repositories.InsertHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsertHistoryService {

    private InsertHistoryRepository inputRepo;
    private InsertHistoryCo2Repository outputRepo;

    @Autowired
    public InsertHistoryService(InsertHistoryRepository insertHistoryRepo,
                                InsertHistoryCo2Repository outputrepo) {
        this.inputRepo = insertHistoryRepo;
        this.outputRepo = outputrepo;
    }

    public InsertHistory createInsertHistory(InsertHistory insertHistory) {
        return inputRepo.saveAndFlush(insertHistory);
    }

    /**
     * find the most recent two insert history of that user.
     *
     * @param username user name.
     * @return a list of insert history, the list can be empty.
     */
    public List<InsertHistoryCo2> findRecentByUsername(String username, int limit) {
        if (limit == -1) {
            return findAllByUserNameSortedByDate(username);
        }
        return outputRepo.findByUserNameOrderByInsertDateDesc(username).stream().limit(limit)
            .collect(Collectors.toList());
    }

    public List<InsertHistoryCo2> findAllByUserNameSortedByDate(String username) {
        return outputRepo.findByUserNameOrderByInsertDateDesc(username);
    }

    /**
     * The all the insert history co2 of that user.
     *
     * @param username users name.
     * @return a list of insert history.
     */
    public List<InsertHistoryCo2> findAllByUserName(String username) {
        return outputRepo.findByUserName(username);
    }

}