package com.khp.flashcard.app;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kanghee on 2/15/2015.
 */
public class CardList extends ArrayList<Card> {

    private Date lastModified;
    private String deckName;

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }



}
