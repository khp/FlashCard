package com.khp.flashcard.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KHP on 04/07/2014.
 */
public class ReviewMode extends Activity {

    private TextView topDisplay;
    private CardList cardList;
    private int currentIndex;
    private boolean isQuestion;
    private Button flipButton;
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        cardList = (CardList) getIntent().getExtras().get("Card List");

        flipButton = (Button) findViewById(R.id.reviewFlipButton);
        prevButton = (Button) findViewById(R.id.reviewPrevButton);
        nextButton = (Button) findViewById(R.id.reviewNextButton);

        flipButton.setOnClickListener(new FlipCardListener());
        prevButton.setOnClickListener(new PrevCardListener());
        nextButton.setOnClickListener(new NextCardListener());

        currentIndex = 0;
        topDisplay = (TextView) findViewById(R.id.topTextView);
        topDisplay.setText(cardList.get(currentIndex).getQuestion());
        isQuestion = true;

    }

    public class FlipCardListener implements View.OnClickListener {
        public void onClick(View view) {
            if (isQuestion) {
                topDisplay.setText(cardList.get(currentIndex).getAnswer());
                isQuestion = false;
            } else {
                topDisplay.setText(cardList.get(currentIndex).getQuestion());
                isQuestion = true;
            }

        }
    }

    public class PrevCardListener implements View.OnClickListener {
        public void onClick(View view) {
            currentIndex--;
            if (currentIndex < 0) {
                currentIndex = cardList.size() - 1;
            }
            topDisplay.setText(cardList.get(currentIndex).getQuestion());
        }
    }

    public class NextCardListener implements View.OnClickListener {
        public void onClick(View view) {
            currentIndex++;
            if (currentIndex == cardList.size()) {
                currentIndex = 0;
            }
            topDisplay.setText(cardList.get(currentIndex).getQuestion());
        }
    }
}
