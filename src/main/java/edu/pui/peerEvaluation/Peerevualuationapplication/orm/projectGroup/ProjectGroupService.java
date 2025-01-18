package edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProjectGroupService {

    private final ProjectGroupRepository projectGroupRepository;

    public ProjectGroupService(ProjectGroupRepository projectGroupRepository) {
        this.projectGroupRepository = projectGroupRepository;
    }

    public ProjectGroup addProjectGroup(ProjectGroup projectGroup){
        return projectGroupRepository.saveAndFlush(projectGroup);
    } 
    
    public List<ProjectGroup> addProjectGroups(List<ProjectGroup> projectGroups){
        return projectGroupRepository.saveAllAndFlush(projectGroups);
    }

}
