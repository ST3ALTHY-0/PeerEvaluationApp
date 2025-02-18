package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

import java.util.List;
import java.util.Map;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import lombok.Data;

/*
 * Class that represents all the data that we will pull out of the CSV export from brightSpace
 */
@Data
public class CSVDataDTO {

    private Student student;
    private ProjectGroup group;
    private GroupCategory groupCategory;
    private Integer pointsWorth;
    private Integer studentGrade;
}
