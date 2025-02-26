package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;

@Service
public class GroupCategoryService extends BaseEntityService<GroupCategory, Integer> {

    private final GroupCategoryRepository groupCategoryRepository;

    @Autowired
    public GroupCategoryService(GroupCategoryRepository groupCategoryRepository){
        this.groupCategoryRepository = groupCategoryRepository;
    }

    //IDK what the fuck is happening here, i changed it because there was problems with shared references, but this is just stupid
     public List<GroupCategory> findAllByInstructorId(Integer instructorId) {
        List<GroupCategory> groupCategories = groupCategoryRepository.findAllByInstructorId(instructorId);
        List<GroupCategory> result = new ArrayList<>();
        for (GroupCategory groupCategory : groupCategories) {
            GroupCategory copy = new GroupCategory();
            copy.setGroupCategoryId(groupCategory.getGroupCategoryId());
            copy.setMyClass(groupCategory.getMyClass());
            copy.setEvaluations(groupCategory.getEvaluations());
            copy.setProjectGroups(new ArrayList<>(groupCategory.getProjectGroups())); // Defensive copying
            result.add(copy);
        }
        return result;
    }

    public GroupCategory addGroupCategory(GroupCategory groupCategory){
        return groupCategoryRepository.saveAndFlush(groupCategory);
    }

      public Optional<GroupCategory> findById(Integer groupCategoryId){
        return groupCategoryRepository.findById(groupCategoryId);
    }

    @Override
    protected BaseEntityRepository<GroupCategory, Integer> getRepository() {
        return groupCategoryRepository;
    }
}
