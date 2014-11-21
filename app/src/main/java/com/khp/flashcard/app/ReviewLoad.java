package com.khp.flashcard.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public class ReviewLoad extends ActionBarActivity {

    String[] SavedFilesArray;
    ArrayList<String> SavedFilesList;
    ListView List;
    MyAdapter adapter;
    ArrayList<Card> CardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_load);

        SavedFilesArray = fileList();
        SavedFilesList = new ArrayList<String>();
        for (int i = 0; i < SavedFilesArray.length; i++) {
            SavedFilesList.add(SavedFilesArray[i]);
        }
        List = (ListView) findViewById(R.id.loadReviewListView);
        adapter = new MyAdapter(this, SavedFilesList);
        List.setAdapter(adapter);
        List.setClickable(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends ArrayAdapter<String> {

        final private Context context;
        private ArrayList<String> FilesList = new ArrayList();

        public MyAdapter(Context context, ArrayList<String> objects) {
            super(context, R.layout.listview_review_load, objects);
            this.context = context;
            FilesList.addAll(objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_review_load, parent, false);
            final TextView textView1 = (TextView) rowView.findViewById(R.id.item1);
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
                    Intent i = new Intent(getApplicationContext(), ReviewMode.class);
                    i.putExtra("result", CardList);
                    startActivity(i);
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
