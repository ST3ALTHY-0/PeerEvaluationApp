package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSVData {

    @CsvBindByName(column = "OrgDefinedId")
    private String puid;

    @CsvBindByName(column = "email")
    private String studentEmail;

    @CsvBindByName(column = "First Name")
    private String firstName;

    @CsvBindByName(column = "Last Name")
    private String lastName;

    @CsvBindByName(column = "Lab Group")
    private String labGroup;

    private CSVProjectData csvProjectData;

    //We dont use this I think
    private  Integer projectPointsGrade;
}