package kapoor.ishan.ca.mapapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView name;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = (TextView)findViewById(R.id.name);
        description = (TextView) findViewById(R.id.desc);
        Bundle bundle = getIntent().getExtras();

        name.setText(bundle.getString("eventName"));
        description.setText(bundle.getString("eventDescription"));

    }
}
