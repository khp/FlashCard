package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.khp.flashcard.app.model.Deck;

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
    private Deck deck;
    private String[] savedFilesArray;
    private ArrayList<String> savedFilesList;
    private ListView listView;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        deck = (Deck) getIntent().getExtras().get("Card listView");
        savedFilesArray = fileList();
        savedFilesList = new ArrayList<String>();
        for (int i = 0; i < savedFilesArray.length; i++) {
            savedFilesList.add(savedFilesArray[i]);
        }
        listView = (ListView) findViewById(R.id.loadListView);
        adapter = new MyAdapter(this, savedFilesList);
        listView.setAdapter(adapter);
        listView.setClickable(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    private class MyAdapter extends ArrayAdapter<String> {

        private ArrayList<String> FilesList = new ArrayList();
        final private Context context;

        public MyAdapter (Context context, List<String> objects) {
            super(context, R.layout.listview_load, objects);
            this.context = context;
            FilesList.addAll(objects);
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_load, parent, false);
            final TextView textView1 = (TextView) rowView.findViewById(R.id.item1);
            Button button = (Button) rowView.findViewById(R.id.deleteButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fDelete = textView1.getText().toString();
                    int temp = savedFilesList.indexOf(fDelete);
                    Log.i(TAG, "Position: " + savedFilesList.get(temp));
                    deleteFile(fDelete);
                    savedFilesList.remove(savedFilesList.indexOf(fDelete));
                    FilesList.remove(FilesList.indexOf(fDelete));
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
                    deck = (Deck) ois.readObject();
                    ois.close();
                    Log.i(TAG, "Question: " + deck.getDeck().get(0).getQuestion());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", (android.os.Parcelable) deck);
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
