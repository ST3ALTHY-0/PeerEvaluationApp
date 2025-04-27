package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
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

    private Integer projectPointsGrade;

    private Integer projectNumerator;

    private Integer projectDenominator;

    private String projectName;

    private String fullProjectName;


    
}




    