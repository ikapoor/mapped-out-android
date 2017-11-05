package kapoor.ishan.ca.mapapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    public static final String TAG = "MAPSACTIVITY";
    private GoogleMap mMap;
    FloatingActionButton addEventButton;
    DatabaseReference databaseEvents;
    ArrayList<events> eventlist;
    DatabaseReference databaseUserEvents;
    Button logOutButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventlist = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        logOutButton = findViewById(R.id.log_out_btn);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
            }
        });
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseUserEvents = FirebaseDatabase.getInstance().getReference("user-events");
        addEventButton = (FloatingActionButton)findViewById(R.id.fab);
        addEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showAddEventDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    events event = snapshot.getValue(events.class);
                    eventlist.add(event);
                    plotPoint(event);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAddEventDialog() {
        View view = this.getLayoutInflater().inflate(R.layout.event_add_form, null);
        final EditText nameedit = view.findViewById(R.id.name_edit_text);
        final EditText description = view.findViewById(R.id.desc_edit_text);
        final EditText locationedit = view.findViewById(R.id.location_edit_text);
        final EditText date = view.findViewById(R.id.date_edit_text);
        final EditText timeedit = view.findViewById(R.id.time_edit_text);
        AlertDialog.Builder  builder= new AlertDialog.Builder(MapsActivity.this)
                .setView(view)
                .setPositiveButton("Add event", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LatLng latLng = getLocationFromAddress(MapsActivity.this, locationedit.getText().toString());
                        createEvent(new events(nameedit.getText().toString(),
                                description.getText().toString(), latLng.latitude, latLng.longitude,
                                date.getText().toString(), timeedit.getText().toString())
                                );
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void createEvent(events events){
        Log.d(TAG, Double.toString(events.getLatitude()) + " " + Double.valueOf(events.getLongitude()));

        String id = databaseEvents.push().getKey();
        events.setId(id);
        databaseEvents.child(id).setValue(events);
        String k =  FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String newID = databaseUserEvents.push().getKey();
        databaseUserEvents.child(id).child(newID).setValue(k.substring(0, k.indexOf("@")));



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);
    }

    public void plotPoint(events event) {
        LatLng point = new LatLng(event.getLatitude(), event.getLongitude());
        mMap.addMarker(new MarkerOptions().position(point).title(event.getName()).snippet(event.getDescription()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent myIntent = new Intent(MapsActivity.this,
                InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("eventName", marker.getTitle());
        bundle.putString("eventDescription", marker.getSnippet());

        for(events events: eventlist){

            if (events.getName().equalsIgnoreCase(marker.getTitle())){
                events currEvent = events;
                bundle.putDouble("latitude", events.getLatitude());
                bundle.putDouble("longitude", events.getLongitude());
                bundle.putString("id", events.getId());
                bundle.putString("date", events.getDate());
                bundle.putString("time", events.getTime());

            }
        }
        myIntent.putExtras(bundle);
        startActivity(myIntent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }


}
