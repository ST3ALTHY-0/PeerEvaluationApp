package edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupCategoryService {

    private final GroupCategoryRepository groupCategoryRepository;

    @Autowired
    public GroupCategoryService(GroupCategoryRepository groupCategoryRepository){
        this.groupCategoryRepository = groupCategoryRepository;
    }

     public List<GroupCategory> findAllByInstructorId(Integer instructorId) {
        List<GroupCategory> groupCategories = groupCategoryRepository.findAllByInstructorId(instructorId);
        List<GroupCategory> result = new ArrayList<>();
        for (GroupCategory groupCategory : groupCategories) {
            GroupCategory copy = new GroupCategory();
            copy.setGroupCategoryId(groupCategory.getGroupCategoryId());
            copy.setCategoryName(groupCategory.getCategoryName());
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

      public GroupCategory findById(Integer groupCategoryId){
        return groupCategoryRepository.findById(groupCategoryId).orElse(null);
    }
}
