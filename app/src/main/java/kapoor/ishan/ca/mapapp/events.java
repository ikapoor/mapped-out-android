package kapoor.ishan.ca.mapapp;

/**
 * Created by Aaditya on 2017-11-04.
 */

public class events {
    String name;
    String description;
    double latitude;
    double longitude;

    public events(String name, String description, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

