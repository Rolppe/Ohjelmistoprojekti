package com.example.ohjelmistoprojekti;

public class HappyHourItem {
    private String mTitle, mFrom, mTo;

    public HappyHourItem(String title, String from, String to) {
        mTitle = title;
        mFrom = from;
        mTo = to;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getFrom() {
        return mFrom;
    }

    public String getTo() {
        return mTo;
    }

}
