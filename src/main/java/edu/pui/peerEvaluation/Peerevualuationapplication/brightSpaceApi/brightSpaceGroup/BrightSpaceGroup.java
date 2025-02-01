package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceGroup;

import java.util.List;

import lombok.Data;

@Data
public class BrightSpaceGroup {
    private long GroupId;
    private String Name;
    private String Code;
    private String Description;
    private List<Long> Enrollments;
    
}


/*
Group Data
 GET /d2l/api/lp/(version)/(orgUnitId)/groupcategories/(groupCategoryId)/groups/
 * {
    "GroupId": <number:D2LID>,
    "Name": <string>,
    "Code": <string>,
    "Description": { <composite:RichText> },
    "Enrollments": [ <number:D2LID>, ... ]
}
 */