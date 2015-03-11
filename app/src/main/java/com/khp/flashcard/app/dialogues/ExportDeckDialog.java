package com.khp.flashcard.app.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.khp.flashcard.app.ManageList;
import com.khp.flashcard.app.R;
import com.khp.flashcard.app.model.Card;
import com.khp.flashcard.app.model.Deck;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by kanghee on 2/17/2015.
 */
public class ExportDeckDialog extends DialogFragment {

    private EditText field;
    private Deck deck;
    private FileWriter writer;
    private CSVWriter csvWriter;
    private TextView out;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_export_deck, null);
        field = (EditText) view.findViewById(R.id.dialogField);
        out = (TextView) view.findViewById(R.id.dialogOut);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String filename = field.getText().toString();
                    filename = filename + ".csv";
                    File newFile = new File(Environment.
                            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                    writer = new FileWriter(newFile);
                    csvWriter = new CSVWriter(writer);
                    String[] toWrite = new String[2];
                    for (Card c : deck.getDeck()) {
                        toWrite[0] = null;
                        toWrite[1] = null;
                        toWrite[0] = c.getQuestion();
                        toWrite[1] = c.getAnswer();
                        csvWriter.writeNext(toWrite);
                    }
                    writer.close();
                    csvWriter.close();
                    MediaScannerConnection.scanFile(getActivity(), new String[]{newFile.getAbsolutePath()}, null, null);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(newFile));
                    getActivity().sendBroadcast(intent);
                } catch (IOException e) {
                    out.setText("Error");
                }
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
