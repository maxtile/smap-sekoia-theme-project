package com.example.sekoia.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class EmptyActivity extends Activity {

    // logcat tag:
    public static final String TAG = "SEKOIA_APP_EmptyActivity";

    String relativeName = "";
    String relativeId = "";   // used for intent to start pictureActivity
    int picPath = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


        // get data from intent:
        Intent intent = getIntent();
        if (intent != null) {
            relativeName = intent.getStringExtra(RelativesChooseActivity.MESSAGE_NAME);
            relativeId = intent.getStringExtra(RelativesChooseActivity.MESSAGE_ID);
            picPath = intent.getIntExtra(RelativesChooseActivity.MESSAGE_PIC, 0);

            Log.d(TAG, "Intent recieved: NAME:" + relativeName + " ID: " + relativeId + "");
        }

        // populate view with name of relative and small image:
        TextView textViev = (TextView) findViewById(R.id.txt_menu_relative);
        textViev.setText(relativeName);
        ImageView imageView = (ImageView) findViewById(R.id.item_imageView_relative);
        imageView.setImageResource(picPath);

    }

}
