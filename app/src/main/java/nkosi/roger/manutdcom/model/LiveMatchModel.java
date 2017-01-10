package nkosi.roger.manutdcom.model;

/**
 * Created by ROGER on 1/7/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveMatchModel implements Serializable {

    @SerializedName("matchID")
    @Expose
    private String matchID;
    @SerializedName("hometeamname")
    @Expose
    private String hometeamname;
    @SerializedName("awayteamname")
    @Expose
    private String awayteamname;
    @SerializedName("matchscore")
    @Expose
    private String matchscore;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("matchdate")
    @Expose
    private String matchdate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("competition")
    @Expose
    private String competition;
    private final static long serialVersionUID = -8548554970420914105L;

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getHometeamname() {
        return hometeamname;
    }

    public void setHometeamname(String hometeamname) {
        this.hometeamname = hometeamname;
    }

    public String getAwayteamname() {
        return awayteamname;
    }

    public void setAwayteamname(String awayteamname) {
        this.awayteamname = awayteamname;
    }

    public String getMatchscore() {
        return matchscore;
    }

    public void setMatchscore(String matchscore) {
        this.matchscore = matchscore;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMatchdate() {
        return matchdate;
    }

    public void setMatchdate(String matchdate) {
        this.matchdate = matchdate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

}
