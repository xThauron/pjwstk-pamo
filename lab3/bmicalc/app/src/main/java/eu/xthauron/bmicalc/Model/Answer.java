package eu.xthauron.bmicalc.Model;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 5150556261742181523L;

    private int id;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
