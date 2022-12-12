package com.example.ohjelmistoprojekti;

public class HappyHourItem {
    //basic constructor class
    private String mTitle, mResult, mPrice, mTimeFrame;

    public HappyHourItem(String title, String timeframe, String result, String price) {
        mTitle = title;
        mTimeFrame = timeframe;
        mResult = result;
        mPrice = price;

    }

    public String getTitle(){
        return mTitle;
    }

    public String getResult() {
        return mResult;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getTimeFrame() {return mTimeFrame;}

}
