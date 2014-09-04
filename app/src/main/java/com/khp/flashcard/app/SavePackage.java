package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHP on 07/07/2014.
 */
public class SavePackage extends Activity {
    private final static String TAG = "SavePackage";
    ArrayList<Card> CardList;
    String[] SavedFilesArray;
    ArrayList<String> SavedFilesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        CardList = (ArrayList) getIntent().getExtras().get("Card List");
        final ListView List = (ListView) findViewById(R.id.saveListView);
        SavedFilesArray = fileList();
        SavedFilesList = new ArrayList();
        for (int i = 0; i < SavedFilesArray.length; i++) {
            SavedFilesList.add(SavedFilesArray[i]);
        }
        final MyAdapter adapter = new MyAdapter(this, SavedFilesList);
        List.setAdapter(adapter);

        final EditText saveName = (EditText) findViewById(R.id.saveEditText1);
        Button saveButton = (Button) findViewById(R.id.saveButton1);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveName.getText().toString() != "") {
                    try {
                        FileOutputStream fos = getApplicationContext().openFileOutput(saveName.getText().toString(), 0);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(CardList);
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private class MyAdapter extends ArrayAdapter<String> {

        private ArrayList<String> FilesList = new ArrayList();
        final private Context context;

        public MyAdapter (Context context, List<String> objects) {
            super(context, R.layout.listview_save, objects);
            this.context = context;
            FilesList.addAll(objects);
            }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_save, parent, false);
            TextView textView1 = (TextView) rowView.findViewById(R.id.item1);
            Button button = (Button) rowView.findViewById(R.id.saveButton);
            Log.i(TAG, "Position: " + position);
            textView1.setText(FilesList.get(position));
            return rowView;
        }
    }
}
