package com.khp.flashcard.app.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kanghee on 2/16/2015.
 */
public class Deck implements Parcelable, Serializable{
    private String title;
    private Date lastModified;
    private ArrayList<Card> deck;

    public Deck(String title) {
        super();
        this.title = title;
        this.deck = new ArrayList();
        lastModified = new Date();
    }

    public static Deck getDeckFromSystem(String fileName, Context context) {
        FileInputStream fis;
        Deck deck = new Deck("error");
        try {
            fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            deck = (Deck) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Deck(Parcel in) {
        Deck serDeck = (Deck) in.readSerializable();
        this.title = serDeck.getTitle();
        this.lastModified = serDeck.getLastModified();
        this.deck = serDeck.getDeck();
    }

    public ArrayList<Card> getIncludedCards () {
        ArrayList<Card> includedCards = new ArrayList<>();
        for (Card c : deck) {
            if (c.isInclude()) {
                includedCards.add(c);
            }
        }
        return includedCards;
    }


    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEmpty() {
        if (this.deck.size() == 0) {
            return true;
        }
        for (Card c : deck) {
            if (c.isInclude()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this);
    }

    public static final Parcelable.Creator<Deck> CREATOR
            = new Parcelable.Creator<Deck>() {
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };
}
