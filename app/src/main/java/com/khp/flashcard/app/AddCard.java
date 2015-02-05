package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by KHP on 04/07/2014.
 */

public class AddCard extends Activity {

    private static final String TAG = "AddCard";
    static final int OPEN_REQUEST = 0;
    static final int SAVE_REQUEST = 1;
    protected int cardPosition = 0;
    private boolean newCard;
    private int test;
    private Card currentCard;
    private EditText question;
    private EditText answer;
    private ArrayList<Card> cardList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            cardList = (ArrayList<Card>) data.getExtras().get("result");
            Log.i(TAG, "Answer: " + cardList.get(0).getQuestion());
            cardPosition = 0;
            currentCard = cardList.get(cardPosition);
            question.setText(currentCard.getQuestion());
            answer.setText(currentCard.getAnswer());
            }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_card_actions, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_package:
                cardList = new ArrayList<Card>();
                cardPosition = 0;
                currentCard = null;
                answer.setText("");
                question.setText("");
                return true;
            case R.id.action_open_package:
                Intent il = new Intent(getApplicationContext(), LoadPackage.class);
                il.putExtra("Card List", cardList);
                startActivityForResult(il, OPEN_REQUEST);
                return true;
            case R.id.action_save_package:
                Intent is = new Intent(getApplicationContext(), SavePackage.class);
                is.putExtra("Card List", cardList);
                startActivityForResult(is, SAVE_REQUEST);
                return true;
            case android.R.id.home:
                Intent ih = new Intent(getApplicationContext(), Main.class);
                startActivity(ih);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        final Button newButton = (Button) findViewById(R.id.addCardNewButton);
        final Button clearButton = (Button) findViewById(R.id.addCardClearButton);
        final Button addButton = (Button) findViewById(R.id.addCardAddButton);
        final Button prevButton = (Button) findViewById(R.id.addCardPrevButton);
        final Button nextButton = (Button) findViewById(R.id.addCardNextButton);

        question = (EditText) findViewById(R.id.addCardEditQuestion);
        answer = (EditText) findViewById(R.id.addCardEditAnswer);

        newButton.setOnClickListener(new NewCardListener());
        clearButton.setOnClickListener(new ClearCardListener());
        addButton.setOnClickListener(new AddCardListener());
        nextButton.setOnClickListener(new NextCardListener());
        prevButton.setOnClickListener(new PrevCardListener());

        // Set currentCard as cardList[0] if loaded, otherwise assume new

        if (cardList == null) {
            cardList = new ArrayList<Card>();
            newCard = true;
        }
        else {
            newCard = false;
            currentCard = cardList.get(cardPosition);
            question.setText(currentCard.getQuestion());
            answer.setText(currentCard.getAnswer());
        }
    }



    public class NewCardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // If cardList is empty, cardPosition must be 0
            if (newCard == true) {
                question.setText(null);
                answer.setText(null);
            }
            else {
                // Else
                currentCard = null;
                question.setText(null);
                answer.setText(null);
                newCard = true;
            }
        }
    }

    public class ClearCardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            question.setText(null);
            answer.setText(null);
        }
    }

    public class AddCardListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // If BOTH fields are not empty
            if ((!question.getText().equals("")) && (!answer.getText().equals(""))) {
                // If current index in cardList exists
                if (newCard) {
                    currentCard = new Card(question.getText().toString(), answer.getText().toString());
                    cardList.add(cardPosition, currentCard);
                    newCard = false;
                    Log.i(TAG, "New card added");
                }
                // If current index in cardList does NOT exist
                else {
                    Card card = new Card(question.getText().toString(), answer.getText().toString());
                    currentCard = card;
                    cardList.set(cardPosition, card);
                    Log.i(TAG, "Additional card added");
                }
            }
            else {
                // Error, must have something in question and answer fields
            }
        }
    }

    public class NextCardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!cardList.isEmpty()) {
                if (cardPosition + 1 == cardList.size()) {
                    setCardPosition(0);
                } else {
                    setCardPosition(cardPosition + 1);
                }
                question.setText(cardList.get(cardPosition).getQuestion());
                answer.setText(cardList.get(cardPosition).getAnswer());
            }
        }
    }

    public class PrevCardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!cardList.isEmpty()) {
                if (cardPosition == 0) {
                    setCardPosition(cardList.size() - 1);
                } else {
                    setCardPosition(cardPosition - 1);
                }
                question.setText(cardList.get(cardPosition).getQuestion());
                answer.setText(cardList.get(cardPosition).getAnswer());
            }
        }
    }




    public int getCardPosition() {
        return cardPosition;
    }

    public void setCardPosition(int set) {
        this.cardPosition = set;
    }
}
