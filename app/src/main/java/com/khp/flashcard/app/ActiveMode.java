package com.khp.flashcard.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KHP on 02/13/2014.
 */
public class ActiveMode extends AbstractReview {

    private TextView count;
    private ArrayList<Card> wrongCardList;
    private int totalCycle;
    private int currentCycle;
    private Button flipButton;
    private ImageButton  correctButton;
    private ImageButton incorrectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        init();
    }

    public void init() {
        super.init();
        wrongCardList = new ArrayList<>();
        shuffleDeckInUse();

        //keep track of how many times user has correctly answered all questions once
        currentCycle = 0;
        totalCycle = 5;
        flipButton = (Button) findViewById(R.id.activeFlipButton);
        correctButton = (ImageButton) findViewById(R.id.activeCorrect);
        incorrectButton = (ImageButton) findViewById(R.id.activeIncorrect);

        flipButton.setOnClickListener(new FlipCardListener());
        correctButton.setOnClickListener(new CorrectCardListener());
        incorrectButton.setOnClickListener(new IncorrectCardListener());

        count = (TextView) findViewById(R.id.activeCountTextView);
        resetCount();
    }



    public void resetCount() {
        this.count.setText(deckInUse.size() + wrongCardList.size() + "/" + deck.getDeck().size());
    }

    public class FlipCardListener implements View.OnClickListener {
        public void onClick(View view) {
            if (!showAnswer) {
                showAnswer();
            } else {
                hideAnswer();
            }
        }
    }

    public class CorrectCardListener implements View.OnClickListener {
        public void onClick(View view) {
            if (deckInUse.size() > 1) {
                deckInUse.remove(0);
            } else if (currentCycle < totalCycle && wrongCardList.size() == 0) {
                currentCycle++;
                resetDeck();
                shuffleDeckInUse();
            } else if (currentCycle == totalCycle && wrongCardList.size() == 0) {

                init();
            } else {
                deckInUse.remove(0);
                deckInUse.addAll(wrongCardList);
                wrongCardList.clear();
                shuffleDeckInUse();
            }
            setQAndA(currentIndex);
            qDisplay.setText(questionString);
            hideAnswer();
            resetCount();
        }
    }

    public class IncorrectCardListener implements View.OnClickListener {
        public void onClick(View view) {
            wrongCardList.add(deckInUse.get(0));
            deckInUse.remove(0);
            if (deckInUse.size() == 0) {
                deckInUse.addAll(wrongCardList);
                wrongCardList.clear();
                shuffleDeckInUse();
            }
            setQAndA(currentIndex);
            qDisplay.setText(questionString);
            hideAnswer();
            resetCount();
        }
    }
}
