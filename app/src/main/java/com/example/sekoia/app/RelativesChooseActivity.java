package com.example.sekoia.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;



public class RelativesChooseActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);

        RelativesFragment relativesFragment = (RelativesFragment) getSupportFragmentManager().findFragmentById(R.id.relativesFragment);
        relativesFragment.setListener(new RelativesFragment.OnMenuItemClickedListener){

        }
    }









}
