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
import com.example.sekoia.app.models.ActivityModel;
import java.util.ArrayList;
import java.util.List;


public class RelativesMenuActivity extends Activity {

    // KEYS used for intent
    public final static String MESSAGE_ID = "com.example.sekoia.app.MESSAGE_ID";
    public final static String MESSAGE_NAME = "com.example.sekoia.app.MESSAGE_NAME";
    public final static String MESSAGE_PIC = "com.example.sekoia.app.MESSAGE_PIC";

    // logcat tag:
    public static final String TAG = "SEKOIA_APP_RelativesMenuActivity";

    String relativeName = "";
    String relativeId = "";   // used for intent to start pictureActivity
    int picPath = 0;

    private List<ActivityModel> activityList = new ArrayList<ActivityModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives_menu);
        Log.d(TAG, "onCreate");

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

        // populate listview
        populateActivityList();
        populateActivityListview();
        registerClickCallback2();
    }

    private void populateActivityList(){
        activityList.add(new ActivityModel(getString(R.string.textView_menu_pictures), R.drawable.icon_pictures));
        activityList.add(new ActivityModel(getString(R.string.textView_menu_calender),R.drawable.icon_calender));
        activityList.add(new ActivityModel(getString(R.string.textView_menu_games),R.drawable.icon_games));
    }

    private void populateActivityListview(){
        Log.d(TAG, "populateListview");
        ArrayAdapter<ActivityModel> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.activityListView);
        list.setAdapter(adapter);
    }


    private void registerClickCallback2(){
        ListView listview = (ListView) findViewById(R.id.activityListView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Log.d(TAG, "onItemClick");
                ActivityModel activityModel = activityList.get(position);
                startActivity(activityModel);

            }
        });
    }


    private void startActivity(ActivityModel activityModel){
        if(activityModel.getActivityName()==getString(R.string.textView_menu_pictures)){

            // TODO: Start pictures activity:
                /*
                Intent intent = new Intent(this, RelativesMenuActivity.class);
                intent.putExtra(MESSAGE_NAME, relativeName);
                intent.putExtra(MESSAGE_ID, relativeId);
                startActivity(intent);
                */

            // TODO - When merged: send intent, remove toast
            String message = "You choose: "+activityModel.getActivityName();
            Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        else{

            Intent intent = new Intent(this, EmptyActivity.class);
            intent.putExtra(MESSAGE_NAME, relativeName);
            intent.putExtra(MESSAGE_ID, relativeId);
            startActivity(intent);

            // TODO - When merged: send intent, remove toast
            String message = "You choose: "+activityModel.getActivityName();
            Toast.makeText(RelativesMenuActivity.this, message, Toast.LENGTH_SHORT).show();
        }

    }





    public class MyListAdapter extends ArrayAdapter<ActivityModel>{
        public MyListAdapter(){
            super(RelativesMenuActivity.this, R.layout.relatives_menu_items, activityList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.relatives_menu_items, parent, false);
            }

            // Find the relative to work with
            ActivityModel activityModel = activityList.get(position);

            // Activity - icon
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_imageView_activity);
            imageView.setImageResource(activityModel.getPicPath());

            // Activity Name:
            TextView nameText = (TextView) itemView.findViewById(R.id.txt_menu_activity);
            nameText.setText(activityModel.getActivityName());

            return itemView;
        }


    }
}
