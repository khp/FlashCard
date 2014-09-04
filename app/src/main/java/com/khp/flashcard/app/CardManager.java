package com.khp.flashcard.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by KHP on 04/07/2014.
 */
public class CardManager extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        final Button newButton = (Button) findViewById(R.id.managerNewButton);
        final Button openButton = (Button) findViewById(R.id.managerOpenButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddCard.class);
                startActivity(i);
            }
        });

    }
}
