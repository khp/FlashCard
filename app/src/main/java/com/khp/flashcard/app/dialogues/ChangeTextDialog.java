package com.khp.flashcard.app.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.khp.flashcard.app.R;
import com.khp.flashcard.app.model.Deck;

/**
 * Created by kanghee on 2/17/2015.
 */
public class ChangeTextDialog extends DialogFragment {

    private EditText field;
    private boolean question;
    private Deck deck;
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_text, null);
        field = (EditText) view.findViewById(R.id.dialogField);

        question = getArguments().getBoolean("question");
        position = getArguments().getInt("position");
        deck = getArguments().getParcelable("deck");

        if (question) {
            field.setText(deck.getDeck().get(position).getQuestion());
        } else {
            field.setText(deck.getDeck().get(position).getAnswer());
        }

        if (question) {
            builder.setMessage("Change Question: ");
        } else {
            builder.setMessage("Change Answer: ");
        }
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (question) {
                    deck.getDeck().get(position).setQuestion(field.getText().toString());
                } else {
                    deck.getDeck().get(position).setAnswer(field.getText().toString());
                }
                ListView lv = (ListView) getActivity().findViewById(R.id.manageListView);
                ArrayAdapter lvAdapter = (ArrayAdapter) lv.getAdapter();
                lvAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
