package edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectGroupController{

    private final ProjectGroupService projectGroupService;

    @Autowired
    public ProjectGroupController(ProjectGroupService projectGroupService){
        this.projectGroupService = projectGroupService;
    }

    // @GetMapping("/projectGroup/{projectGroupId}")
    // public ProjectGroup getGroup(@RequestParam Integer projectGroupId) {
    //     return projectGroupService.findById(projectGroupId);
    // }
    

}
