package com.khp.flashcard.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by KHP on 04/07/2014.
 */



public class ReviewMode extends AbstractReview {

    private int currentIndex;
    private Button flipButton;
    private Button prevButton;
    private Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        init();
    }

    public void init() {
        super.init();

        flipButton = (Button) findViewById(R.id.reviewFlipButton);
        prevButton = (Button) findViewById(R.id.reviewPrevButton);
        nextButton = (Button) findViewById(R.id.reviewNextButton);

        flipButton.setOnClickListener(new FlipCardListener());
        prevButton.setOnClickListener(new PrevCardListener());
        nextButton.setOnClickListener(new NextCardListener());
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

    public class PrevCardListener implements View.OnClickListener {
        public void onClick(View view) {
            currentIndex--;
            if (currentIndex < 0) {
                currentIndex = deck.getDeck().size() - 1;
            }
            setQAndA(currentIndex);
            qDisplay.setText(questionString);
            hideAnswer();
        }
    }

    public class NextCardListener implements View.OnClickListener {
        public void onClick(View view) {
            currentIndex++;
            if (currentIndex == deck.getDeck().size()) {
                currentIndex = 0;
            }
            setQAndA(currentIndex);
            qDisplay.setText(questionString);
            hideAnswer();
        }
    }
}
