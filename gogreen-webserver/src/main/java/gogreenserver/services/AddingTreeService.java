package gogreenserver.services;

import gogreenserver.entity.Tree;
import gogreenserver.repositories.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddingTreeService {

    private TreeRepository treeRepository;

    @Autowired
    public AddingTreeService(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    public Tree addTree(Tree tree) {
        return this.treeRepository.saveAndFlush(tree);
    }

    public List<Tree> findAll() {
        return treeRepository.findAll();
    }

}
