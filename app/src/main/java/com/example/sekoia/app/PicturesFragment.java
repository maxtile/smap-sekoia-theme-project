package com.example.sekoia.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Maximilian on 25.05.14.
 */
public class PicturesFragment extends Fragment {
    private OnPicturesFragmentInteraction aCallback;
    private Button takePicButton;
    private Button choosePicButton;

    public PicturesFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            aCallback = (OnPicturesFragmentInteraction) activity;
        } catch (ClassCastException exception){
            throw new ClassCastException(activity.toString() + "must implement " +
                    "OnOnPicturesFragmentInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        choosePicButton = (Button)rootView.findViewById(R.id.choosePictureButton);
        choosePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aCallback.onChoosePictureButtonClicked();
            }
        });
        takePicButton = (Button)rootView.findViewById(R.id.takePictureButton);
        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aCallback.onTakePictureButtonClicked();
            }
        });
        return rootView;
    }

    public interface OnPicturesFragmentInteraction{
        public void onTakePictureButtonClicked();
        public void onChoosePictureButtonClicked();
    }
}
