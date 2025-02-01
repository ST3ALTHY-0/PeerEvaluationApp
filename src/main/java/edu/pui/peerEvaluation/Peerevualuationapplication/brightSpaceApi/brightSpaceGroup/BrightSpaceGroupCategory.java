package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceGroup;

import java.util.List;

import lombok.Data;
//Hold the data about group categories
@Data
public class BrightSpaceGroupCategory {

    private long GroupCategoryId;
    private String Name;
    private String Description;
    private int EnrollmentStyle;
    private Integer EnrollmentQuantity;
    private Integer MaxUsersPerGroup;
    private boolean AutoEnroll;
    private boolean RandomizeEnrollments;
    private List<Long> Groups;
    private boolean AllocateAfterExpiry;
    private String SelfEnrollmentExpiryDate;
    private Long RestrictedByOrgUnitId;
    private boolean DescriptionsVisibleToEnrolees;
    
}

/*
Group Category
GET /d2l/api/lp/(version)/(orgUnitId)/groupcategories/
 * {
    "GroupCategoryId": <number:D2LID>,
    "Name": <string>,
    "Description": { <composite:RichText> },
    "EnrollmentStyle": <number:GRPENROLL_T>,
    "EnrollmentQuantity": <number>|null,
    "MaxUsersPerGroup": <number>|null,
    "AutoEnroll": <boolean>,
    "RandomizeEnrollments": <boolean>,
    "Groups": [ <number:D2LID>, ... ],
    "AllocateAfterExpiry": <boolean>,
    "SelfEnrollmentExpiryDate": <string:UTCDateTime>|null,
    "RestrictedByOrgUnitId": <number:D2LID>|null,
    "DescriptionsVisibleToEnrolees": <boolean>
}
 */