package kapoor.ishan.ca.mapapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishan on 2017-11-05.
 */

public class usersAdapter extends ArrayAdapter<String> {


    ArrayList<String> strings;
    Context context;

    public usersAdapter(@NonNull Context context, int resource, ArrayList<String> list) {
        super(context, resource);
        this.strings = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View personView = inflater.inflate(R.layout.list_item_user, parent, false);
        TextView textView = personView.findViewById(R.id.name);
        textView.setText(strings.get(position));
        return personView;

    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }
}
