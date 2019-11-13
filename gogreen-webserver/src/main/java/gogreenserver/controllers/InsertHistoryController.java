package gogreenserver.controllers;

import gogreenserver.entity.InsertHistory;
import gogreenserver.entity.InsertHistoryCo2;
import gogreenserver.services.InsertHistoryService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InsertHistoryController {

    private final Logger logger;
    private InsertHistoryService insertHistoryService;

    @Autowired
    public InsertHistoryController(InsertHistoryService insertHistoryService, Logger logger) {
        this.insertHistoryService = insertHistoryService;
        this.logger = logger;
    }

    /**
     * Get a single history record.
     */
    @GetMapping(value = "/insertHistory/{user_Name}")
    public ResponseEntity<List<InsertHistoryCo2>> findAllById(
        @PathVariable("user_Name") String userName,
        @RequestHeader(name = "limit", defaultValue = "2") int limit, Authentication auth) {

        logger.debug("GET /insertHistory/" + userName + " accessed by: " + auth.getName());

        List<InsertHistoryCo2> histories = this.insertHistoryService.findRecentByUsername(userName,
            limit);

        logger.debug(histories);

        return new ResponseEntity<>(histories,
            !histories.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * The end point for retrieving the amount of activities of a user.
     *
     * @param userName username.
     * @return a response entity.
     */
    @GetMapping(value = "/insertHistory/amount/{user_Name}")
    public ResponseEntity<String> findActivityAmountByUserName(
        @PathVariable("user_Name") String userName) {

        logger.debug("GET /insertHistory/" + userName);

        return new ResponseEntity<String>(
            String.valueOf(this.insertHistoryService.findAllByUserName(userName).size()),
            HttpStatus.OK);
    }

    /**
     * The endpoint for retrieving active days of a user, which is calculated by
     * subtracting the date of first insertion from the data of the last insertion.
     *
     * @param userName username.
     * @return a response entity.
     */
    @GetMapping(value = "/insertHistory/days/{user_Name}")
    public ResponseEntity<String> findActiveDaysByUserName(
        @PathVariable("user_Name") String userName) {

        logger.debug("GET /insertHistory/days/" + userName);

        List<InsertHistoryCo2> list = this.insertHistoryService
            .findAllByUserNameSortedByDate(userName);

        if (list.isEmpty()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }

        logger.debug(list);

        long days = Duration
            .between(list.get(list.size() - 1).getInsertDate(), list.get(0).getInsertDate())
            .toDays();

        return new ResponseEntity<>(String.valueOf(days), HttpStatus.OK);
    }

    /**
     * This endpoint is used to create a new entry in the InsertHistory table.
     *
     * @param insertHistory This is an object of type insertHistory.
     * @return responseEntity of type String with status code OK if successful.
     */
    @PostMapping(value = "/insertHistory")
    public ResponseEntity<String> createInsertHistory(@RequestBody InsertHistory insertHistory,
                                                      Authentication auth) {

        logger.debug("POST /insertHistory/ accessed by " + auth.getName());

        if (!auth.getName().equals(insertHistory.getUserName())) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        this.insertHistoryService.createInsertHistory(insertHistory);
        return new ResponseEntity<String>("Successfully insertHistory for user : ", HttpStatus.OK);
    }

}