package com.example.sekoia.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RelativesMenuActivity extends ActionBarActivity {


    public final static String MESSAGE_ID = "com.example.sekoia.app.MESSAGE";
    public final static String MESSAGE_FULLNAME = "com.example.sekoia.app.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives_menu);

        // set view:

        Intent intent = getIntent();
        if(intent != null){
            String fullName = intent.getExtras().getString("MESSAGE_FULLNAME");
            int id = Integer.parseInt(intent.getExtras().getString("MESSAGE_FULLNAME"));
        }



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
