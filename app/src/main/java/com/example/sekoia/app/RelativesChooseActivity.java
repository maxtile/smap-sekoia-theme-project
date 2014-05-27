package com.example.sekoia.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sekoia.app.dummy.RelativeModel;

import java.util.ArrayList;
import java.util.List;


public class RelativesChooseActivity extends Activity {

    // Message used for intent
    // public final static String MESSAGE = "com.example.sekoia.app.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);

        populateRelativesList();
        populatListview();
        registerClickCallback();
        }

    private List<RelativeModel> model = new ArrayList<RelativeModel>();

    private void populateRelativesList(){
        model.add(new RelativeModel( "Kalle", "Hansen"));
        model.add(new RelativeModel("Nancy", "Bergren"));
    }

    private void populatListview(){
        ArrayAdapter<RelativeModel> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.relativesListView);
        list.setAdapter(adapter);
    }




    private void registerClickCallback(){
        ListView list = (ListView) findViewById(R.id.relativesListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                RelativeModel relativemodel = model.get(position);

                // toast
                String message = "You clicked position " + position
                        + " Fornavn" + relativemodel.getFirstName();
                Toast.makeText(RelativesChooseActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class MyListAdapter extends ArrayAdapter<RelativeModel>{
        public MyListAdapter(){
            super(RelativesChooseActivity.this, R.layout.relatives_items, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.relatives_items, parent, false);
            }

            // Find the car to work with.
            RelativeModel relativeModel = model.get(position);

            // Fill the view
            //ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            //imageView.setImageResource(relativeModel.getIconID());

            // Make:
            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            makeText.setText(relativeModel.getFirstName());

            // Year:
            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
            yearText.setText("" + relativeModel.getLastName());

            // Condition:
            //TextView condionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
            //condionText.setText(relativeModel.getCondition());

            return itemView;
        }


    }








    }










