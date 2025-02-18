package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.brightSpaceUser;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

//brightspace has two endpoints we need to call to get user info
//we will call /d2l/api/lp/(version)/users/whoami [GET] to get basic data about user, namely their userId
//which we then use to call GET /d2l/api/lp/(version)/profile/user/(userId) to get further data about the user namely their email address
@Data
public class BrightSpaceUserExtended{
    private String Nickname;
    private Birthday Birthday;
    private String HomeTown;
    private String Email;
    private String HomePage;
    private String HomePhone;
    private String BusinessPhone;
    private String MobilePhone;
    private String FaxNumber;
    private String Address1;
    private String Address2;
    private String City;
    private String Province;
    private String PostalCode;
    private String Country;
    private String Company;
    private String JobTitle;
    private String HighSchool;
    private String University;
    private String Tagline;
    private String Hobbies;
    private String FavMusic;
    private String FavTVShows;
    private String FavMovies;
    private String FavBooks;
    private String FavQuotations;
    private String FavWebSites;
    private String FutureGoals;
    private String FavMemory;
    private List<SocialMediaUrl> SocialMediaUrls;
    private String ProfileIdentifier;

    @Data
    public static class Birthday {
        private int Month;
        private int Day;
    }

    @Data
    public static class SocialMediaUrl {
        private String Name;
        private String Url;
    }

}
