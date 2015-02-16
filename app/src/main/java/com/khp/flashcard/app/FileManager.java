package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by kanghee on 2/15/2015.
 */
public abstract class FileManager extends Activity {


    private String[] savedFilesArray;
    private ArrayList<String> savedFilesList;
    private ListView listView;
    private FileListAdapter adapter;
    private CardList cardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardList = (CardList) getIntent().getExtras().get("Card listView");
        savedFilesArray = fileList();
        savedFilesList = new ArrayList<String>();
        for (int i = 0; i < savedFilesArray.length; i++) {
            savedFilesList.add(savedFilesArray[i]);
        }
        listView = (ListView) findViewById(R.id.loadReviewListView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
