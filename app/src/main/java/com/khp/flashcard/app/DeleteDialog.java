package com.khp.flashcard.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by kanghee on 2/20/2015.
 */

public class DeleteDialog extends DialogFragment {

    private Deck deck;
    private int position;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete?");
        position = getArguments().getInt("position");
        deck = getArguments().getParcelable("deck");

        builder.setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deck.getDeck().remove(position);
                ListView lv = (ListView) getActivity().findViewById(R.id.manageListView);
                ArrayAdapter lvAdapter = (ArrayAdapter) lv.getAdapter();
                lvAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}