package com.example.clone1.Websocket;

public class Messagerr {

    private String name;
    private String Content;

    public Messagerr() {
    }

    public Messagerr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Messagerr(String name, String Content) {
        this.name = name;
        this.Content = Content;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

}
