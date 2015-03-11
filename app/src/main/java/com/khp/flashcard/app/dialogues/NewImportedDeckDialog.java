package com.khp.flashcard.app.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.khp.flashcard.app.ManageList;
import com.khp.flashcard.app.R;
import com.khp.flashcard.app.model.Deck;

/**
 * Created by kanghee on 2/17/2015.
 */
public class NewImportedDeckDialog extends DialogFragment {

    private EditText field;
    private Deck deck;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_imported_deck, null);
        field = (EditText) view.findViewById(R.id.dialogField);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deck.setTitle(field.getText().toString());
                Intent i = new Intent(getActivity(), ManageList.class);
                i.putExtra("Deck", (android.os.Parcelable) deck);
                startActivity(i);
            }
        }).setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
    public void setDeck (Deck deck) {
        this.deck = deck;
    }
}
