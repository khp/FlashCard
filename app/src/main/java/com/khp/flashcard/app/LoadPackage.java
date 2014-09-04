package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHP on 07/07/2014.
 */
public class LoadPackage extends Activity {
    private final static String TAG = "LoadPackage";
    ArrayList<Card> CardList;
    String[] SavedFilesArray;
    ArrayList<String> SavedFilesList;
    ListView List;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        CardList = (ArrayList) getIntent().getExtras().get("Card List");
        SavedFilesArray = fileList();
        SavedFilesList = new ArrayList<String>();
        for (int i = 0; i < SavedFilesArray.length; i++) {
            SavedFilesList.add(SavedFilesArray[i]);
        }
        List = (ListView) findViewById(R.id.loadListView);
        adapter = new MyAdapter(this, SavedFilesList);
        List.setAdapter(adapter);
        List.setClickable(true);

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
            View rowView = inflater.inflate(R.layout.listview_load, parent, false);
            final TextView textView1 = (TextView) rowView.findViewById(R.id.item1);
            Button button = (Button) rowView.findViewById(R.id.loadButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fDelete = textView1.getText().toString();
                    int temp = SavedFilesList.indexOf(fDelete);
                    Log.i(TAG, "Position: " + SavedFilesList.get(temp));
                    deleteFile(fDelete);
                    SavedFilesList.remove(SavedFilesList.indexOf(fDelete));
                    FilesList.remove(FilesList.indexOf(fDelete));
                    Log.i(TAG, "Position: " + SavedFilesList.get(temp));
                    adapter.notifyDataSetChanged();
                }
            });
            Log.i(TAG, "Position: " + position);
            textView1.setText(FilesList.get(position));
            rowView.setOnClickListener(new viewClickListener());
            return rowView;
        }
        public class viewClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                TextView textView1 = (TextView) view.findViewById(R.id.item1);
                String fileName = textView1.getText().toString();
                textView1.setText("Loaded");
                FileInputStream fis;
                try {
                    fis = openFileInput(fileName);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    CardList = (ArrayList<Card>) ois.readObject();
                    ois.close();
                    Log.i(TAG, "Question: " + CardList.get(0).getQuestion());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", CardList);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
