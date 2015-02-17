package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kanghee on 2/16/2015.
 */
public class MainList extends Activity {

    private ListView listView;
    private String[] savedFilesArray;
    private ArrayList<String> savedFilesList;
    private ListViewAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        savedFilesArray = fileList();
        savedFilesList = new ArrayList();
        for (int i = 0; i < savedFilesArray.length; i++) {
            savedFilesList.add(savedFilesArray[i]);
        }
        listView = (ListView) findViewById(R.id.mainListView);
        savedFilesList.add("Create New Deck");
        adapter = new ListViewAdapter(this, savedFilesList);
        listView.setAdapter(adapter);
    }
    private class ListViewAdapter extends ArrayAdapter<String> {

        final private Context context;
        private ArrayList<String> FilesList = new ArrayList();

        public ListViewAdapter(Context context, ArrayList<String> objects) {
            super(context, R.layout.listview_review_load, objects);
            this.context = context;
            FilesList.addAll(objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_main_list, parent, false);
            final TextView titleText = (TextView) rowView.findViewById(R.id.item1);
            final TextView numberText = (TextView) rowView.findViewById(R.id.number_of_cards);
            final TextView modifiedText = (TextView) rowView.findViewById(R.id.date_modified);
            if (position == FilesList.size() - 1) {
                titleText.setText(FilesList.get(position));
                rowView.setOnClickListener(new viewClickListener(new Deck("thing")));
                numberText.setText("");
                modifiedText.setText("");
                return rowView;
            }
            titleText.setText(FilesList.get(position));
            Deck deck = Deck.getDeckFromSystem(FilesList.get(position), context);
            numberText.setText(Integer.toString(deck.getDeck().size()) + " cards");
            modifiedText.setText(deck.getLastModified().toString());
            rowView.setOnClickListener(new viewClickListener(deck));
            return rowView;
        }
        public class viewClickListener implements View.OnClickListener {

            private Deck deck;

            public viewClickListener (Deck deck) {
            this.deck = deck;
            }

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddCard.class);
                i.putExtra("Deck", (android.os.Parcelable) deck);
                startActivity(i);
            }
        }
    }
}
