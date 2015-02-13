package com.khp.flashcard.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by KHP on 02/13/2014.
 */
public class ActiveMode extends Activity {

    private TextView topDisplay;
    private ArrayList<Card> cardList;
    private ArrayList<Card> shuffledCardList;
    private ArrayList<Card> wrongCardList;
    private int totalCycle;
    private int currentCycle;
    private int currentIndex;
    private boolean isQuestion;
    private Button flipButton;
    private Button  correctButton;
    private Button incorrectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        cardList = (ArrayList) getIntent().getExtras().get("Card List");

        shuffledCardList = new ArrayList<>();
        wrongCardList = new ArrayList<>();
        this.resetShuffledDeck();

        //keep track of how many times user has correctly answered all questions once
        currentCycle = 0;
        totalCycle = 5;

        flipButton = (Button) findViewById(R.id.activeFlipButton);
        correctButton = (Button) findViewById(R.id.activeCorrectButton);
        incorrectButton = (Button) findViewById(R.id.activeIncorrectButton);

        flipButton.setOnClickListener(new FlipCardListener());
        correctButton.setOnClickListener(new CorrectCardListener());
        incorrectButton.setOnClickListener(new IncorrectCardListener());

        currentIndex = 0;
        topDisplay = (TextView) findViewById(R.id.topTextView);
        topDisplay.setText(shuffledCardList.get(0).getQuestion());
        isQuestion = true;

    }

    public void resetShuffledDeck() {
        shuffledCardList.clear();
        shuffledCardList.addAll(cardList);
        Collections.shuffle(shuffledCardList);
    }

    public class FlipCardListener implements View.OnClickListener {
        public void onClick(View view) {
            if (isQuestion) {
                topDisplay.setText(shuffledCardList.get(0).getAnswer());
                isQuestion = false;
            } else {
                topDisplay.setText(shuffledCardList.get(0).getQuestion());
                isQuestion = true;
            }

        }
    }

    public class CorrectCardListener implements View.OnClickListener {
        public void onClick(View view) {
            if (shuffledCardList.size() > 0) {
                shuffledCardList.remove(0);
            } else if (currentCycle < totalCycle) {
                currentCycle++;
                resetShuffledDeck();
            } else {
                // end conditions
            }
            topDisplay.setText(shuffledCardList.get(0).getQuestion());
        }
    }

    public class IncorrectCardListener implements View.OnClickListener {
        public void onClick(View view) {
            wrongCardList.add(shuffledCardList.get(0));
            if (shuffledCardList.size() > 0) {
                shuffledCardList.remove(0);
            } else {
                shuffledCardList.addAll(wrongCardList);
            }
            topDisplay.setText(shuffledCardList.get(currentIndex).getQuestion());
        }
    }
}