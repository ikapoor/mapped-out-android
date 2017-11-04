package kapoor.ishan.ca.mapapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aaditya on 2017-11-04.
 */

public class events implements Parcelable{
    String name;
    String description;
    double latitude;
    double longitude;

    public events(Parcel in){
        name = in.readString();
        description = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public events(String name, String description, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }

    public static final Parcelable.Creator<events> CREATOR = new Parcelable.Creator<events>(){

        public events createFromParcel(Parcel in){
            return new events(in);
        }

        public events[] newArray(int size){

            return new events[size];
        }


    };

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

