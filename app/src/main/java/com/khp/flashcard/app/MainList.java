package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private NewDeckDialog newDialog;
    private OpenDeckDialog openDialog;

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
        init();
    }
    private void init () {

        refreshList();

        listView = (ListView) findViewById(R.id.mainListView);
        adapter = new ListViewAdapter(this, savedFilesList);
        listView.setAdapter(adapter);
    }

    public ArrayList<String> refreshList () {
        // Load files from system to an array
        savedFilesArray = fileList();
        savedFilesList = new ArrayList();
        for (int i = 0; i < savedFilesArray.length; i++) {
            savedFilesList.add(savedFilesArray[i]);
        }
        // Add entry for ListViewAdapter to return View for New Deck rowView
        savedFilesList.add("Create New Deck");
        return savedFilesList;
    }

    // Inner class extending ArrayAdapter for ListView
    public class ListViewAdapter extends ArrayAdapter<String> {

        final private Context context;
        private ArrayList<String> filesList = new ArrayList();

        public ListViewAdapter(Context context, ArrayList<String> objects) {
            super(context, R.layout.listview_review_load, objects);
            this.context = context;
            filesList.addAll(objects);
        }
        public ArrayList<String> getFilesList() {
            return filesList;
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            // Always get LayoutInflater instead of instantiating it
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_main_list, parent, false);
            final TextView titleText = (TextView) rowView.findViewById(R.id.item1);
            final TextView numberText = (TextView) rowView.findViewById(R.id.number_of_cards);
            final TextView modifiedText = (TextView) rowView.findViewById(R.id.date_modified);
            // For last rowView create a "New Deck" selection instead
            if (position == filesList.size() - 1) {
                titleText.setText(filesList.get(position));
                rowView.setOnClickListener(new NewClickListener());
                numberText.setText("");
                modifiedText.setText("");
                return rowView;
            }
            Deck deck = Deck.getDeckFromSystem(filesList.get(position), context);
            titleText.setText(deck.getTitle());

            if (deck.getDeck().size() == 1) {
                numberText.setText(Integer.toString(deck.getDeck().size()) + " card");
            } else {
                numberText.setText(Integer.toString(deck.getDeck().size()) + " cards");
            }

            modifiedText.setText(deck.getLastModified().toString());
            rowView.setOnClickListener(new DeckClickListener(deck));
            return rowView;
        }

        // OnClickListener for starting new activity with given deck
        public class DeckClickListener implements View.OnClickListener {

            private Deck deck;

            public DeckClickListener(Deck deck) {
            this.deck = deck;
            }

            public void onClick(View view) {
                openDialog = new OpenDeckDialog();
                openDialog.setDeck(deck);
                openDialog.show(getFragmentManager(), "open deck newDialog");
            }
        }

        // OnClickListener which opens a NewDeckDialog
        public class NewClickListener implements View.OnClickListener {

            public void onClick(View view) {
                newDialog = new NewDeckDialog();
                newDialog.show(getFragmentManager(), "new deck newDialog");
            }
        }
    }

    // Controls ActionBar icons
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_package:
                newDialog = new NewDeckDialog();
                newDialog.show(getFragmentManager(), "new deck newDialog");
                return true;

            default:
                return true;
        }
    }

}
