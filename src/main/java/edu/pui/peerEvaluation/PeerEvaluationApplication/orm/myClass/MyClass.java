package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass;

import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "class")
@EqualsAndHashCode(exclude = {"projects"})
@ToString(exclude = {"instructor", "projects"})
public class MyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    private String className;

    private String classCode;

    // manyToOne means that many classes can have the same instructor
    @ManyToOne
    // JoinColumn means that the instructor will be related to classes via
    // instructor_id (primary key)
    @JoinColumn(name = "instructorId")
    private Instructor instructor;

    @OneToMany(mappedBy = "myClass", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "myClass", cascade = CascadeType.ALL)
    private List<GroupCategory> groupCategories;

}
