package com.khp.flashcard.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by kanghee on 2/15/2015.
 */

public abstract class FileListAdapter extends ArrayAdapter<String> {

    final private Context context;
    final private int resource;

    private ArrayList<String> FilesList = new ArrayList();

    public FileListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        FilesList.addAll(objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        final TextView textView1 = (TextView) rowView.findViewById(R.id.item1);
        textView1.setText(FilesList.get(position));
        return rowView;
    }
    public abstract class viewClickListener implements View.OnClickListener {
        @Override
        public abstract void onClick(View view);
    }
}
