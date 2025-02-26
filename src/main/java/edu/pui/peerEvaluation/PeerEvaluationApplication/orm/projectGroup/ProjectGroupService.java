package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectGroupService {

    private final ProjectGroupRepository projectGroupRepository;

    public ProjectGroupService(ProjectGroupRepository projectGroupRepository) {
        this.projectGroupRepository = projectGroupRepository;
    }

    public ProjectGroup saveProjectGroup(ProjectGroup projectGroup){
        return projectGroupRepository.save(projectGroup);
    }

    public List<ProjectGroup> saveAllProjectGroups(List<ProjectGroup> projectGroup){
        return projectGroupRepository.saveAll(projectGroup);
    }

    public ProjectGroup addProjectGroup(ProjectGroup projectGroup) {
        return projectGroupRepository.saveAndFlush(projectGroup);
    }

    public List<ProjectGroup> addProjectGroups(List<ProjectGroup> projectGroups) {
        return projectGroupRepository.saveAllAndFlush(projectGroups);
    }

    public ProjectGroup findById(Integer projectGroupId) {
        return projectGroupRepository.findByProjectId(projectGroupId).orElse(null);
    }

    public List<ProjectGroup> findAllByStudentId(Integer studentId){
        return projectGroupRepository.findAllByStudentId(studentId);
    }

    public ProjectGroup findProjectGroupByEvaluationIdAndStudentId(Integer evaluationId, Integer studentId){
        return projectGroupRepository.findProjectGroupByEvaluationIdAndStudentId(evaluationId, studentId).orElse(null);
    }

    public Optional<ProjectGroup> findByGroupNameAndProject(String groupName, Project project){
        return projectGroupRepository.findByGroupNameAndProject(groupName, project); 
    }

}
