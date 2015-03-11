package com.khp.flashcard.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.khp.flashcard.app.dialogues.NewImportedDeckDialog;
import com.khp.flashcard.app.fileio.CSVFilter;
import com.khp.flashcard.app.model.Deck;
import com.khp.flashcard.app.parser.CSVParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kanghee on 3/9/2015.
 */
public class ImportDeck extends Activity {

    private Deck deck;
    private File file;
    private List<File> fileList;
    private ListView listView;
    private FileAdapter adapter;
    private CSVParser parser;
    private NewImportedDeckDialog newDeckDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_deck);
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        fileList = new ArrayList<>();
        File[] fileArray = file.listFiles(new CSVFilter());
        for (File f : fileArray) {
            fileList.add(f);
        }
        listView = (ListView) findViewById(R.id.loadListView);
        adapter = new FileAdapter(this, fileList);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        parser = new CSVParser();
    }

    private class FileAdapter extends ArrayAdapter<File> {

        private ArrayList<File> FilesList = new ArrayList();
        final private Context context;

        public FileAdapter(Context context, List<File> objects) {
            super(context, R.layout.listview_load, objects);
            this.context = context;
            FilesList.addAll(objects);
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_import, parent, false);
            final TextView textView1 = (TextView) rowView.findViewById(R.id.fileName);
            textView1.setText(FilesList.get(position).getName());
            rowView.setOnClickListener(new ViewClickListener(position));
            return rowView;
        }
        public class ViewClickListener implements View.OnClickListener {

            private int position;

            public ViewClickListener (int position) {
                super();
                this.position = position;
            }

            @Override
            public void onClick(View view) {
                try {
                    deck = new Deck("");
                    deck = parser.parse(FilesList.get(position));
                    newDeckDialog = new NewImportedDeckDialog();
                    newDeckDialog.setDeck(deck);
                    newDeckDialog.show(getFragmentManager(), "newDeckDialog");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
