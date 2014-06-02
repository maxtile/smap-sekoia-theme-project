package com.example.sekoia.app.models;

import android.app.Application;

/**
 * Created by Maxi on 02-06-14.
 */
public class SekoiaApp extends Application {
    private static SekoiaApp _instance;

    public SekoiaApp() {
        super();
        _instance = this;
    }

    public static SekoiaApp getContext() {
        return _instance;
    }

    private Relative currentRelative;
    public Relative getCurrentRelative(){
        if(currentRelative == null) throw new NullPointerException("currentRelative is Null!");
        return currentRelative;
    }

    public void setCurrentRelative(Relative currentRelative){
        if(currentRelative == null) throw new NullPointerException("Value cannot be null!");
        this.currentRelative = currentRelative;
    }
}
