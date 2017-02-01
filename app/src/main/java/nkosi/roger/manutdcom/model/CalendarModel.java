package nkosi.roger.manutdcom.model;

/**
 * Created by MPHILE on 1/11/2017.
 */
public class CalendarModel {
    public String against, homeAway, datePlayed, score, competition;

    public CalendarModel(BuildCalender calender) {
        this.score = calender.score;
        this.against = calender.against;
        this.homeAway = calender.homeAway;
        this.competition = calender.competition;
        this.datePlayed = calender.datePlayed;
    }

    public static class BuildCalender{
        public String against, homeAway, datePlayed, score, competition;

        public BuildCalender setAgainst(String against){
            this.against = against;
            return BuildCalender.this;
        }

        public BuildCalender setHomeAway(String homeAway){
            this.homeAway = homeAway;
            return BuildCalender.this;
        }

        public BuildCalender setDatePlayed(String datePlayed){
            this.datePlayed = datePlayed;
            return BuildCalender.this;
        }

        public BuildCalender setScore(String score){
            this.score = score;
            return BuildCalender.this;
        }

        public BuildCalender setComp(String competition){
            this.competition = competition;
            return BuildCalender.this;
        }

        public CalendarModel buildCalender(){
            return new CalendarModel(BuildCalender.this);
        }
    }
}
