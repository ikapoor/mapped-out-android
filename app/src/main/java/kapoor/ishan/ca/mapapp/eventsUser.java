package kapoor.ishan.ca.mapapp;

/**
 * Created by ishan on 2017-11-04.
 */

public class eventsUser {
    private String userId;
    private String  eventId;

    public eventsUser(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
