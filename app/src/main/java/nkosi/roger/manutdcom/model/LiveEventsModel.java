package nkosi.roger.manutdcom.model;

/**
 * Created by ROGER on 12/27/2016.
 */
public class LiveEventsModel {

    public String eventIcon, eventTitle, eventTime, eventDescription;

    public LiveEventsModel(LiveEventBuilder builder) {
        this.eventDescription = builder.eventDescription;
        this.eventIcon = builder.eventIcon;
        this.eventTime = builder.eventTime;
        this.eventTitle = builder.eventTitle;

    }

    public static class LiveEventBuilder{
        String eventIcon, eventTitle, eventTime, eventDescription;
        public LiveEventBuilder setEventIcon(String eventIcon){
            this.eventIcon = eventIcon;
            return LiveEventBuilder.this;
        }
        public LiveEventBuilder setEventTitle(String eventTitle){
            this.eventTitle = eventTitle;
            return LiveEventBuilder.this;
        }
        public LiveEventBuilder setEventTime(String eventTime){
            this.eventTime = eventTime;
            return LiveEventBuilder.this;
        }
        public LiveEventBuilder setEventDescription(String description){
            this.eventDescription = description;
            return LiveEventBuilder.this;
        }
        public LiveEventsModel buildHeadlines(){
            return new LiveEventsModel(LiveEventBuilder.this);
        }
    }
}
