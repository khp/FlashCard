package com.khp.flashcard.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KHP on 04/07/2014.
 */
public class ReviewMode extends Activity {

    private TextView topDisplay;
    private ArrayList<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        cardList = (ArrayList) getIntent().getExtras().get("Card List");
        topDisplay = (TextView) findViewById(R.id.topTextView);
        topDisplay.setText(cardList.get(0).getQuestion());
    }
}
