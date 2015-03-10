package com.khp.flashcard.app.parser;

import com.khp.flashcard.app.model.Card;
import com.khp.flashcard.app.model.Deck;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by kanghee on 3/9/2015.
 */
public class CSVParser {

    private FileReader in;
    private String[] nextLine;
    private String question;
    private String answer;
    private CSVReader reader;
    private Deck deck;
    public Deck parse (File file) throws FileNotFoundException {

        deck = new Deck("");
        in = new FileReader(file);
        reader = new CSVReader(in, ',', '"', false);
        Iterator<String[]> i = reader.iterator();
        while (i.hasNext()) {
            nextLine = i.next();
            question = nextLine[0];
            answer = nextLine[1];
            deck.getDeck().add(new Card(question, answer));
        }
        return deck;
    }
}
