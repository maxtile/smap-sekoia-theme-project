package com.example.sekoia.app.models;


public class ActivityModel {

    String activityName;
    int picPath;


    public ActivityModel(String activityName, int picPath) {
        super();
        this.activityName = activityName;
        this.picPath = picPath;

    }


    public String getActivityName() {
        return activityName;
    }
    public int getPicPath() {
        return picPath;
    }

}


