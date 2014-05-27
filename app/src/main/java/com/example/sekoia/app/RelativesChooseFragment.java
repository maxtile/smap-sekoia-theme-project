package com.example.sekoia.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.sekoia.app.dummy.DummyContent;
import com.example.sekoia.app.dummy.RelativeModel;


public class RelativesChooseFragment extends Fragment {

    OnMenuItemClickedListener mListener;

    public interface OnMenuItemClickedListener{
        public void onMenuItemClicked(int position, RelativeModel relative);
    }


    public void setListener(OnMenuItemClickedListener listener){
        mListener = listener;
    }




}
