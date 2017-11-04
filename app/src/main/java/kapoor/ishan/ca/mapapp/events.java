package kapoor.ishan.ca.mapapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aaditya on 2017-11-04.
 */

public class events {
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String date;
    private String time;
    private String id;

    public events(){

    }



    public events(String name, String description, double latitude, double longitude, String Date, String Time) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = Date;
        this.time = Time;
    }


    public void setName(String Name) {
        this.name = Name;
    }

    public void setDescription(String DEscription) {
        this.description = DEscription;
    }

    public void setLatitude(double LAtitude) {
        this.latitude = LAtitude;
    }

    public void setLongitude(double LOngitude) {
        this.longitude = LOngitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String Daate) {
        this.date = Daate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

