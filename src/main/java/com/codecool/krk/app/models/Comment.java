package com.codecool.krk.app.models;

public class Comment {
    private String nick;
    private String text;

    public Comment(){}

    public Comment(String nick, String text) {
        this.nick = nick;
        this.text = text;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
