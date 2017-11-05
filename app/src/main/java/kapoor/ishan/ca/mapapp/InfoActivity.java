package kapoor.ishan.ca.mapapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class InfoActivity extends AppCompatActivity {

    TextView name;
    TextView description;
    Button joinBtn;
    Button directionBtn;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = (TextView)findViewById(R.id.name);
        description = (TextView) findViewById(R.id.desc);
        Bundle bundle = getIntent().getExtras();

        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        name.setText(bundle.getString("eventName"));
        description.setText(bundle.getString("eventDescription"));

        joinBtn = (Button)findViewById(R.id.join_btn);
        directionBtn = (Button)findViewById(R.id.direction_btn);

        joinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        directionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
                String uri = "http://maps.google.com/maps?saddr=&daddr=" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });



    }
}
