package kapoor.ishan.ca.mapapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    events event1;
    events event2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        event1 = new events("Ball Up", "A friendly game of pickup basketball", 43.6532, -79.3832);
        event2 = new events("Soccer Game", "A game of soccer", 41.6532, -83.3832);

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
        plotPoint(event1);
        plotPoint(event2);

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
        myIntent.putExtras(bundle);
        startActivity(myIntent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
