package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.khp.flashcard.app.dialogues.ChangeTextDialog;
import com.khp.flashcard.app.dialogues.DeleteDialog;
import com.khp.flashcard.app.model.Card;
import com.khp.flashcard.app.model.Deck;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by kanghee on 2/17/2015.
 */
public class ManageList extends Activity {


    private ListView listView;
    private ListViewAdapter adapter;
    private ChangeTextDialog textDialog;
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

        public View getView(final int position, View convertView, ViewGroup parent) {

            // Always get LayoutInflater instead of instantiating it
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_display_card, parent, false);
            final TextView qText = (TextView) rowView.findViewById(R.id.qTextView);
            final TextView aText = (TextView) rowView.findViewById(R.id.aTextView);
            final CheckBox includeBox = (CheckBox) rowView.findViewById(R.id.checkBox);
            includeBox.setChecked(deck.getDeck().get(position).isInclude());
            includeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    deck.getDeck().get(position).setInclude(isChecked);
                }
            });
            qText.setText(deck.getDeck().get(position).getQuestion());
            aText.setText(deck.getDeck().get(position).getAnswer());
            ViewClickListener longClickListener = new ViewClickListener(position);
            qText.setOnClickListener(new TextClickListener(position, true));
            qText.setOnLongClickListener(longClickListener);
            aText.setOnClickListener(new TextClickListener(position, false));
            aText.setOnLongClickListener(longClickListener);
            rowView.setOnLongClickListener(new ViewClickListener(position));
            return rowView;
        }
        public class ViewClickListener implements View.OnLongClickListener {

            private int position;

            public ViewClickListener (int position) {
                this.position = position;
            }

            @Override
            public boolean onLongClick(View v) {
                Bundle bund1 = new Bundle();
                bund1.putInt("position", position);
                bund1.putParcelable("deck", deck);
                DeleteDialog delete = new DeleteDialog();
                delete.setArguments(bund1);
                delete.show(getFragmentManager(), "delete card dialog");
                return false;
            }
        }
        // OnClickListener for starting new activity with given deck
        public class TextClickListener implements View.OnClickListener {

            private int position;
            private boolean question;

            public TextClickListener(int position, boolean question) {
                this.position = position;
                this.question = question;
            }

            public void onClick(View view) {
                Bundle bund1 = new Bundle();
                bund1.putBoolean("question", question);
                bund1.putInt("position", position);
                bund1.putParcelable("deck", deck);
                textDialog = new ChangeTextDialog();
                textDialog.setArguments(bund1);
                textDialog.show(getFragmentManager(), "new card dialog");
                save();
            }
        }
    }

    public void save() {
        try {
            deleteFile(deck.getTitle());
            FileOutputStream fos = getApplicationContext().openFileOutput(deck.getTitle(), 0);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(deck);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Controls ActionBar icons
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_card:
                deck.getDeck().add(new Card("Question", "Answer"));
                save();
                adapter.notifyDataSetChanged();
            case R.id.action_save_deck:
                save();
                return true;
            case android.R.id.home:
                save();
                Intent ih = new Intent(getApplicationContext(), MainList.class);
                startActivity(ih);
                return true;
            default:
                return true;
        }
    }

    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ListView list = (ListView) v;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int position = info.position;
        DeleteDialog delete = new DeleteDialog();
        Bundle bund1 = new Bundle();
        bund1.putInt("position", position);
        bund1.putParcelable("deck", deck);
        delete.setArguments(bund1);
        delete.show(getFragmentManager(), "delete card dialog");
    }
}
