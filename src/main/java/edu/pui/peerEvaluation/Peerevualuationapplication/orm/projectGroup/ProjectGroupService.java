package edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;

@Service
public class ProjectGroupService {

    private final ProjectGroupRepository projectGroupRepository;

    public ProjectGroupService(ProjectGroupRepository projectGroupRepository) {
        this.projectGroupRepository = projectGroupRepository;
    }

    public ProjectGroup addProjectGroup(ProjectGroup projectGroup) {
        return projectGroupRepository.saveAndFlush(projectGroup);
    }

    public List<ProjectGroup> addProjectGroups(List<ProjectGroup> projectGroups) {
        return projectGroupRepository.saveAllAndFlush(projectGroups);
    }

    public ProjectGroup findById(int projectGroupId) {
        return projectGroupRepository.findByProjectId(projectGroupId);
    }

}
