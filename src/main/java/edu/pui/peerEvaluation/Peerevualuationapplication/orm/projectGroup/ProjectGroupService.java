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

    public ProjectGroup findById(Integer projectGroupId) {
        return projectGroupRepository.findByProjectId(projectGroupId).orElse(null);
    }

    // public ProjectGroup findByEvaluationIdAndStudentId(Integer evaluationId, Integer studentId, Integer projectId){
    //     return projectGroupRepository.findByEvaluationIdAndStudentIdAndProjectId(evaluationId, studentId, projectId).orElse(null);
    // }

    public List<ProjectGroup> findAllByStudentId(Integer studentId){
        return projectGroupRepository.findAllByStudentId(studentId);
    }

    public ProjectGroup findProjectGroupByEvaluationIdAndStudentId(Integer evaluationId, Integer studentId){
        return projectGroupRepository.findProjectGroupByEvaluationIdAndStudentId(evaluationId, studentId).orElse(null);
    }

}
