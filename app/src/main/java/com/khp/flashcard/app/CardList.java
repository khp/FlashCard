package com.khp.flashcard.app;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kanghee on 2/16/2015.
 */
public class CardList extends ArrayList<Card> {
    private String title;

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    private Date lastModified;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
