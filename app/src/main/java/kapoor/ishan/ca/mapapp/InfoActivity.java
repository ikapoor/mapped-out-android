package kapoor.ishan.ca.mapapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class InfoActivity extends AppCompatActivity {

    TextView name;
    TextView description;
    Button joinBtn;
    Button directionBtn;
    String eventId;
    ListView listView;

    usersAdapter adapter;
    double latitude;
    double longitude;
    DatabaseReference databaseEventUser;
    ArrayList<String> users= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        name = (TextView)findViewById(R.id.name);
        description = (TextView) findViewById(R.id.desc);
        Bundle bundle = getIntent().getExtras();
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new usersAdapter(this, R.layout.list_item_user, users);
        listView.setAdapter(adapter);
        eventId = bundle.getString("id");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        name.setText(bundle.getString("eventName"));
        description.setText(bundle.getString("eventDescription"));

        databaseEventUser = FirebaseDatabase.getInstance().getReference("user-events").child(eventId);

        joinBtn = (Button)findViewById(R.id.join_btn);
        directionBtn = (Button)findViewById(R.id.direction_btn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String k =  FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String newID = databaseEventUser.push().getKey();
                databaseEventUser.child(newID).setValue(k.substring(0, k.indexOf("@")));

            }
        });




        directionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String uri = "http://maps.google.com/maps?saddr=&daddr=" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseEventUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Log.d(dataSnapshot.toString(), dataSnapshot.getChildren().toString());
               users.clear();
               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   users.add(snapshot.getValue(String.class));
               }
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseEventUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
