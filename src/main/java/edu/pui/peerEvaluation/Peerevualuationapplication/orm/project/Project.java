package edu.pui.peerEvaluation.Peerevualuationapplication.orm.project;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "project")
@EqualsAndHashCode(exclude = {"myClass", "evaluation"})
@ToString(exclude = {"evaluation"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    private String projectName;

    private int pointsWorth;

    // @Version
    // private int version;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Evaluation evaluation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classId", referencedColumnName = "classId")
    private MyClass myClass;

    // this class is referenced by
    // project_group
    // @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    // private List<GroupCategory> groupCategories;

    // evaluation
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Feedback feedback;
}
