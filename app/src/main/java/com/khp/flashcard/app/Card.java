package com.khp.flashcard.app;

import java.io.Serializable;

/**
 * Created by KHP on 04/07/2014.
 */
public class Card implements Serializable {

    private String question;
    private String answer;

    Card(String q, String a) {
        question = q;
        answer = a;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
