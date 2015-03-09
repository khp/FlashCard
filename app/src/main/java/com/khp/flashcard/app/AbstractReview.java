package com.khp.flashcard.app;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by KHP on 23/02/2015.
 */
public abstract class AbstractReview extends Activity{

    public static final String BLANK_ANSWER = " ";
    protected TextView qDisplay;
    protected TextView aDisplay;
    protected TextView aTitle;
    protected Deck deck;
    protected ArrayList<Card> deckInUse;
    protected ArrayList<Card> fullDeck;
    protected int currentIndex;
    protected boolean showAnswer;
    protected String questionString;
    protected String answerString;
    protected Resources res;

    public void init() {
        deck = (Deck) getIntent().getExtras().get("Deck");
        deckInUse = new ArrayList<>();
        fullDeck = deck.getIncludedCards();
        deckInUse.addAll(fullDeck);
        res = getResources();
        currentIndex = 0;
        setQAndA(currentIndex);
        aTitle = (TextView) findViewById(R.id.answerTitleTextView);
        qDisplay = (TextView) findViewById(R.id.questionTextView);
        qDisplay.setText(questionString);
        aDisplay = (TextView) findViewById(R.id.answerTextView);
        hideAnswer();
    }

    public void setQAndA(int currentIndex){
        answerString = String.format(res.getString(R.string.review_answer),
                deckInUse.get(currentIndex).getAnswer());
        questionString = String.format(res.getString(R.string.review_question),
                deckInUse.get(currentIndex).getQuestion());
    }

    public void showAnswer() {
        aTitle.setVisibility(View.VISIBLE);
        aDisplay.setText(answerString);
        showAnswer = true;

    }
    public void hideAnswer() {
        aTitle.setVisibility(View.INVISIBLE);
        aDisplay.setText(BLANK_ANSWER);
        showAnswer = false;
    }

    public void shuffleDeckInUse() {
        Collections.shuffle(deckInUse);
    }

    public void resetDeck() {
        deckInUse.clear();
        deckInUse.addAll(fullDeck);
    }
}
