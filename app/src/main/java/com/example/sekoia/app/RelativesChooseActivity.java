package com.example.sekoia.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    // KEYS used for intent
    public final static String MESSAGE_ID = "com.example.sekoia.app.MESSAGE_ID";
    public final static String MESSAGE_NAME = "com.example.sekoia.app.MESSAGE_NAME";
    public final static String MESSAGE_PIC = "com.example.sekoia.app.MESSAGE_PIC";


    // Logcat TAG
    public static final String TAG = "SEKOIA_APP_RelativesChooseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);
        Log.d(TAG, "onCreate");

        populateRelativesList();
        populatListview();
        registerClickCallback();
        }

    private List<RelativeModel> model = new ArrayList<RelativeModel>();

    private void populateRelativesList(){
        model.add(new RelativeModel(111, "Kalle", "Hansen",R.drawable.kalle, "Grenen", "Lejlighed 3"));
        model.add(new RelativeModel(222, "Nancy", "Berggren", R.drawable.nancy, "Kvisten", "Lejlighed 14"));
    }

    private void populatListview(){
        Log.d(TAG, "populateListview");
        ArrayAdapter<RelativeModel> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.relativesListView);
        list.setAdapter(adapter);
    }




    private void registerClickCallback(){
        ListView list = (ListView) findViewById(R.id.relativesListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Log.d(TAG, "onItemClick");
                RelativeModel relativemodel = model.get(position);

                // fire intent to start new activity
                startMenuActivity(relativemodel);
            }
        });

    }


    private void startMenuActivity(RelativeModel relativeModel){

        //TODO when merged: remove toast
        String message = "You chose: " + relativeModel.getFirstName()+" ID: "+relativeModel.getId();
        Toast.makeText(RelativesChooseActivity.this, message, Toast.LENGTH_SHORT).show();



        //Intent to start the RelativesMenuActivity
        Intent intent = new Intent(this, RelativesMenuActivity.class);
        intent.putExtra(MESSAGE_ID,""+relativeModel.getId());
        intent.putExtra(MESSAGE_NAME,relativeModel.getFirstName());
        intent.putExtra(MESSAGE_PIC,relativeModel.getPicPath());
        startActivity(intent);

        Log.d(TAG, "Intent send: start activity: RelativesMenuActivity");
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

            // Find the relative to work with
            RelativeModel relativeModel = model.get(position);

            // Image
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(relativeModel.getPicPath());

            // Full Name:
            TextView nameText = (TextView) itemView.findViewById(R.id.item_txtName);
            nameText.setText(relativeModel.getFirstName()+" "+ relativeModel.getLastName());

            // Nursery home:
            TextView homeText = (TextView) itemView.findViewById(R.id.item_txtHome);
            homeText.setText("" + relativeModel.getHomeName());

            // Apartment:
            TextView apartmentText = (TextView) itemView.findViewById(R.id.item_txtApartment);
            apartmentText.setText(relativeModel.getRoom());

            return itemView;
        }


    }








    }










