package edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup;

import org.springframework.stereotype.Service;

@Service
public class ProjectGroupService {

    private final ProjectGroupRepository projectGroupRepository;

    public ProjectGroupService(ProjectGroupRepository projectGroupRepository) {
        this.projectGroupRepository = projectGroupRepository;
    }

}
