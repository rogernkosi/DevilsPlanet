
package nkosi.roger.manutdcom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedMatchModel {

    @SerializedName("featureMatchID")
    @Expose
    private String featureMatchID;
    @SerializedName("against")
    @Expose
    private String homeTeam;
    @SerializedName("homeAway")
    @Expose
    private String awayTeam;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("matchdate")
    @Expose
    private String matchdate;
    @SerializedName("featureImg")
    @Expose
    private String featureImg;
    @SerializedName("featureText")
    @Expose
    private String featureText;

    public String getFeatureMatchID() {
        return featureMatchID;
    }

    public void setFeatureMatchID(String featureMatchID) {
        this.featureMatchID = featureMatchID;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMatchdate() {
        return matchdate;
    }

    public void setMatchdate(String matchdate) {
        this.matchdate = matchdate;
    }

    public String getFeatureImg() {
        return featureImg;
    }

    public void setFeatureImg(String featureImg) {
        this.featureImg = featureImg;
    }

    public String getFeatureText() {
        return featureText;
    }

    public void setFeatureText(String featureText) {
        this.featureText = featureText;
    }

}