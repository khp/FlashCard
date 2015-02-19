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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by kanghee on 2/17/2015.
 */
public class ManageList extends Activity {


    private ListView listView;
    private ListViewAdapter adapter;
    private OpenDeckDialog openDialog;
    private Deck deck;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_list_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);
        deck = (Deck) this.getIntent().getExtras().get("Deck");
        getActionBar().setTitle(deck.getTitle());
        init();
    }
    private void init () {


        listView = (ListView) findViewById(R.id.manageListView);
        adapter = new ListViewAdapter(this);
        listView.setAdapter(adapter);
    }

    // Inner class extending ArrayAdapter for ListView
    private class ListViewAdapter extends ArrayAdapter<Card> {

        final private Context context;
        public ListViewAdapter(Context context) {
            super(context, R.layout.listview_review_load, deck.getDeck());
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            // Always get LayoutInflater instead of instantiating it
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_display_card, parent, false);
            final TextView qText = (TextView) rowView.findViewById(R.id.qTextView);
            final TextView aText = (TextView) rowView.findViewById(R.id.aTextView);
            qText.setText(deck.getDeck().get(position).getQuestion());
            aText.setText(deck.getDeck().get(position).getAnswer());

            rowView.setOnClickListener(new CardClickListener(position));
            return rowView;
        }

        // OnClickListener for starting new activity with given deck
        public class CardClickListener implements View.OnClickListener {

            private int position;

            public CardClickListener(int position) {
                this.position = position;
            }

            public void onClick(View view) {
                openDialog = new OpenDeckDialog();
                openDialog.setDeck(deck);
                openDialog.show(getFragmentManager(), "open deck newDialog");
            }
        }
    }

    // Controls ActionBar icons
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_card:
                deck.getDeck().add(new Card("new", "card"));
            case R.id.action_save_deck:
                try {
                    deleteFile(deck.getTitle());
                    FileOutputStream fos = getApplicationContext().openFileOutput(deck.getTitle(), 0);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(deck);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case android.R.id.home:
                Intent ih = new Intent(getApplicationContext(), MainList.class);
                startActivity(ih);
                return true;
            default:
                return true;
        }
    }

}
