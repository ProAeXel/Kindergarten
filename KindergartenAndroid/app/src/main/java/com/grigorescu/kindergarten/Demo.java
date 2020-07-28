package com.grigorescu.kindergarten;

public class Demo {

    private String title, subtitle;

    public Demo(String title, String subtitle)
    {
        this.title = title;
        this.subtitle = subtitle;

    }
    public Demo()
    {}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return subtitle;
    }

    public void setText(String subtitle) {
        this.subtitle = subtitle;
    }


}
