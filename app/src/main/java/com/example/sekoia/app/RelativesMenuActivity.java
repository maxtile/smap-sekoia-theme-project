package com.example.sekoia.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class RelativesMenuActivity extends Activity {


    public final static String MESSAGE_ID = "com.example.sekoia.app.MESSAGE";
    public final static String MESSAGE_FULLNAME = "com.example.sekoia.app.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives_menu);

        // set view:


       /* Intent intent = getIntent();
        if(intent != null){
            //String fullName = intent.getExtras().getString("MESSAGE_FULLNAME");
            int id = Integer.parseInt(intent.getExtras().getString("MESSAGE_FULLNAME"));
            String message = ""+id;
            Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

        }
        */




        RelativeLayout rlayoutPictures = (RelativeLayout)findViewById(R.id.relativeLayoutPictures);
        rlayoutPictures.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //TOAST
                String message = getString(R.string.menuActivity_message_pictures);
                Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

                //Start pictures activity
                //Intent intent = new Intent(this, RelativesMenuActivity.class);
                //startActivity(intent);


            }
        });

        RelativeLayout rlayoutCalender = (RelativeLayout)findViewById(R.id.relativeLayoutCalender);
        rlayoutCalender.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //Start activity - to be implemented
                String message = getString(R.string.menuActivity_message_calender);
                Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

        RelativeLayout rlayoutGames = (RelativeLayout)findViewById(R.id.relativeLayoutGames);
        rlayoutGames.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //Start activity - to be implemented
                String message = getString(R.string.menuActivity_message_games);
                Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });


        //




    }


    // Methods for button-click
    public void pictureClicked(){
        String message = "You chose Picture!!";
        Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();
    }




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
