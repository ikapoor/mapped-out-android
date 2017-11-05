package kapoor.ishan.ca.mapapp;

/**
 * Created by ishan on 2017-11-05.
 */

public class EventsUserID {
   private String Id;
   private eventsUser eventsUser;
    public EventsUserID(){}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public kapoor.ishan.ca.mapapp.eventsUser getEventsUser() {
        return eventsUser;
    }

    public void setEventsUser(kapoor.ishan.ca.mapapp.eventsUser eventsUser) {
        this.eventsUser = eventsUser;
    }
}
