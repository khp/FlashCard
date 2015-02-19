package com.khp.flashcard.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kanghee on 2/18/2015.
 */
public class OpenDeckDialog extends DialogFragment {
    private ListView listView;
    private ListViewAdapter adapter;
    private Deck deck;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_open_deck, null);
        listView = (ListView) view.findViewById(R.id.openDeckListView);
        ArrayList<String> buttonList = new ArrayList();
        buttonList.add("Review Deck");
        buttonList.add("Active Study");
        buttonList.add("Modify Deck");
        buttonList.add("Delete");
        adapter = new ListViewAdapter(getActivity(), buttonList);
        listView.setAdapter(adapter);
        builder.setView(view);
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    // Set deck
    public void setDeck (Deck deck) {
        this.deck = deck;
    }

    // Inner class extending ArrayAdapter for ListView in PopupWindow
    private class ListViewAdapter extends ArrayAdapter<String> {

        final private Context context;
        private ArrayList<String> buttonList;
        public ListViewAdapter (Context context, ArrayList<String> objects) {
            super(context, R.layout.dialog_open_deck, objects);
            this.context = context;
            this.buttonList = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_button, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.buttonTextView);
            textView.setText(buttonList.get(position));
            textView.setOnClickListener(new ViewClickListener(position));

            return view;
        }

        public class ViewClickListener implements View.OnClickListener {

            final private int num;
            public ViewClickListener(int i) {
                super();
                this.num = i;
            }
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                switch (num) {
                    case 0: i = new Intent(getActivity(), ReviewMode.class);
                            break;
                    case 1: i = new Intent(getActivity(), ActiveMode.class);
                            break;
                    case 2: i = new Intent(getActivity(), ManageList.class);
                            break;
                    case 3: getContext().deleteFile(deck.getTitle());
                            ListView lv = (ListView) getActivity().findViewById(R.id.mainListView);
                            MainList.ListViewAdapter lvAdapter = (MainList.ListViewAdapter) lv.getAdapter();
                            lvAdapter.remove(deck.getTitle());
                            lvAdapter.getFilesList().remove(deck.getTitle());
                            lvAdapter.notifyDataSetChanged();
                            dismiss();
                            return;
                }
                i.putExtra("Deck", (Parcelable) deck);
                startActivity(i);
            }
        }
    }
}
