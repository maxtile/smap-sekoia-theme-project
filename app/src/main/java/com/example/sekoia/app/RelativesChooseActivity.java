package com.example.sekoia.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sekoia.app.Service.GetRelativesService;
import com.example.sekoia.app.dummy.RelativeModel;
import java.util.ArrayList;
import java.util.List;


public class RelativesChooseActivity extends Activity {

    // Message used for intent
    public final static String MESSAGE_ID = "com.example.sekoia.app.MESSAGE";
    public final static String MESSAGE_FULLNAME = "com.example.sekoia.app.MESSAGE";

    GetRelativesService mService;
    boolean mBound = false;
    private List<RelativeModel> model = new ArrayList<RelativeModel>();
    ArrayAdapter<RelativeModel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);

        //mAdapter = new ArrayAdapter<String>(this, android.R.layout.activity_relatives, model);
        //setListAdapter(mAdapter);

        //populateRelativesList();
        populatListview();
        registerClickCallback();
        }

   // private void populateRelativesList(){
   //     model.add(new RelativeModel(111, "Kalle", "Hansen",R.drawable.kalle, "Grenen", "Lejlighed 3"));
   // }

    //private void populateRelativesList(){
    //    model.add(new RelativeModel(111, "Kalle", "Hansen",R.drawable.kalle, "Grenen", "Lejlighed 3"));
    //    model.add(new RelativeModel(222, "Nancy", "Berggren", R.drawable.nancy, "Kvisten", "Lejlighed 14"));
    //}

    public void populatListview(){
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

                // fire intent to start new activity
                startMenuActivity(relativemodel);
            }
        });

    }

    private void startMenuActivity(RelativeModel relativeModel){

        //toast
        String message = "You chose: " + relativeModel.getFirstName()+" ID: "+relativeModel.getId();
        Toast.makeText(RelativesChooseActivity.this, message, Toast.LENGTH_SHORT).show();

        //Intent to start the RelativesMenuActivity
        Intent intent = new Intent(this, RelativesMenuActivity.class);
        //intent.putExtra(MESSAGE_ID,""+relativeModel.getId());
        //intent.putExtra(MESSAGE_FULLNAME,relativeModel.getFirstName()+" "+relativeModel.getLastName());
        startActivity(intent);

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

    //Kode for service
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, GetRelativesService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        if(mBound) {
            mService.startDownload();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(GetRelativesService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }



    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            GetRelativesService.IProtocolBinder binder = (GetRelativesService.IProtocolBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt(GetRelativesService.RESULT);
                if(resultCode == GetRelativesService.RESULT_OK) {
                    if(mBound) {
                        model = mService.getData();
                        populatListview();
                        //model = getTitles(mService.getData());
                        //mAdapter = new ArrayAdapter<RelativesChooseActivity>(RelativesChooseActivity.this, android.R.layout.activity_relatives, model);
                        //RelativesChooseActivity.this.setListAdapter(mAdapter);

                    }
                }else{
                    Toast.makeText(RelativesChooseActivity.this, "Error downloading", Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    /*private List<String> getTitles(List<RelativeModel> relatives) {
        ArrayList<RelativeModel> result = new ArrayList<RelativeModel>();
        for(RelativeModel s : relatives) {
            //result.add(s.getLastName());
            //model.add(s.getFirstName());

            //result.add(s.getLastName());
        }
        return result;*/
    //}





}










