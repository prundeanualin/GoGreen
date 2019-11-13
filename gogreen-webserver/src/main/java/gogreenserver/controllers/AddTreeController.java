package gogreenserver.controllers;

import gogreenserver.entity.Tree;
import gogreenserver.services.AddingTreeService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddTreeController {

    private Logger logger;
    private AddingTreeService addingTreeService;


    @Autowired
    public AddTreeController(Logger logger, AddingTreeService addingTreeService) {
        this.logger = logger;
        this.addingTreeService = addingTreeService;
    }


    /**
     * This endpoint will create a new tuple in the table tree_add.
     *
     * @param tree tree to be added.
     * @param auth auth.
     * @return a response entity.
     */
    @PostMapping(value = "/addTree")
    public ResponseEntity<String> addTree(@RequestBody Tree tree,
                                          Authentication auth) {

        final String userName = tree.getUserName();

        logger.debug("POST /addTree/ with userName header \"" + userName + "\" accessed by: "
            + auth.getName());

        if (!auth.getName().equals(userName)) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        this.addingTreeService.addTree(tree);
        return new ResponseEntity<String>(
            "Successfully saved tree entry for user :" + userName, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllTrees")
    public ResponseEntity<List<Tree>> getAllTrees() {
        return new ResponseEntity<>(this.addingTreeService.findAll(),
            HttpStatus.OK);
    }


}
