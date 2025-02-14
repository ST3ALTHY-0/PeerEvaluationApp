package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceSCVParser;

import java.util.List;
import java.util.Map;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;

import lombok.Data;

/*
 * Class that represents all the data that we will pull out of the CSV export from brightSpace
 */
@Data
public class CSVDataDTO {

    private List<Student> students;
    private List<ProjectGroup> groups;
    private GroupCategory groupCategory;
    private Integer pointsWorth;
    private Map<Student, Integer> studentStartingGrades;
}
