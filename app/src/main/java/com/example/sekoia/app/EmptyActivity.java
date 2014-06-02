package com.example.sekoia.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sekoia.app.models.SekoiaApp;


public class EmptyActivity extends Activity {

    // logcat tag:
    public static final String TAG = "SEKOIA_APP_EmptyActivity";

   /* String relativeName = "";
    String relativeId = "";   // used for intent to start pictureActivity
    int picPath = 0;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        TextView textViev = (TextView) findViewById(R.id.txt_menu_relative);
        textViev.setText(SekoiaApp.getContext().getCurrentRelative().getFirstName());
        ImageView imageView = (ImageView) findViewById(R.id.item_imageView_relative);

        String picture = SekoiaApp.getContext().getCurrentRelative().getPicPath();
        int resID = getResources().getIdentifier(picture , "drawable", getPackageName());
        imageView.setImageResource(resID);

    }

}
