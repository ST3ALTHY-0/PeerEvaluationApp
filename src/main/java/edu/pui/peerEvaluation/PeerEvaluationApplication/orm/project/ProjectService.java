package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public Project findById(Integer id){
        return projectRepository.findById(id).orElse(null);
    }
}
