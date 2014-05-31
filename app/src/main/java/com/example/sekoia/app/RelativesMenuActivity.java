package com.example.sekoia.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class RelativesMenuActivity extends Activity {

    // KEYS used for intent
    public final static String MESSAGE_ID = "com.example.sekoia.app.MESSAGE_ID";
    public final static String MESSAGE_FULLNAME = "com.example.sekoia.app.MESSAGE_NAME";
    public final static String MESSAGE_PIC = "com.example.sekoia.app.MESSAGE_PIC";

    // logcat tag:
    public static final String TAG = "SEKOIA_APP_RelativesMenuActivity";

    String relativeName = "";
    String relativeId = "";   // used for intent to start pictureActivity
    int picPath = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives_menu);
        Log.d(TAG, "onCreate");

        // get data from intent:

        Intent intent = getIntent();
        if(intent != null){
            relativeName = intent.getStringExtra(RelativesChooseActivity.MESSAGE_NAME);
            relativeId = intent.getStringExtra(RelativesChooseActivity.MESSAGE_ID);
            picPath = intent.getIntExtra(RelativesChooseActivity.MESSAGE_PIC, 0);

            Log.d(TAG, "Intent recieved: NAME:"+relativeName+" ID: "+relativeId+"");

            //TODO when merged: comment out this toast:
            String message = "From previous activity: "+ relativeName+", id: "+relativeId;
            Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        // populate view with name of relative and small image:
        TextView textViev = (TextView) findViewById(R.id.txt_menu_relative);
        textViev.setText(relativeName);
        ImageView imageView = (ImageView)findViewById(R.id.item_imageView_relative);
        imageView.setImageResource(picPath);

        //------- onClickListeners-------//



        RelativeLayout rlayoutPictures = (RelativeLayout)findViewById(R.id.relativeLayoutPictures);
        rlayoutPictures.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d(TAG, "onClick_Pictures");

                //TODO - When merged: Toast has to be commented out
                String message = getString(R.string.menuActivity_message_pictures);
                Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

                // TODO - When merged:
                // Start pictures activity

                /*
                Intent intent = new Intent(this, RelativesMenuActivity.class);
                intent.putExtra(MESSAGE_NAME, relativeName);
                intent.putExtra( MESSAGE_ID, relativeId);
                startActivity(intent);
                */

            }
        });

        RelativeLayout rlayoutCalender = (RelativeLayout)findViewById(R.id.relativeLayoutCalender);
        rlayoutCalender.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d(TAG, "onClick_Calender");

                //Start activity - to be implemented
                String message = getString(R.string.menuActivity_message_calender);
                Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

        RelativeLayout rlayoutGames = (RelativeLayout)findViewById(R.id.relativeLayoutGames);
        rlayoutGames.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d(TAG, "onClick_Games");

                //Start activity - to be implemented
                String message = getString(R.string.menuActivity_message_games);
                Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });


        //




    }


    //TODO - delete the following unused methods:

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.relatives_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
