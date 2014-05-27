package com.example.sekoia.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by Maximilian on 25.05.14.
 */
public class PicturesFragment extends Fragment {
    private OnPicturesFragmentInteraction aCallback;
    private Button addPictureButton;
    private GridView imageGridView;

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

        PicturesActivity.adapter = new CustomGridAdapter((PicturesActivity)getActivity(),
                PicturesActivity.Bitmaps);
        imageGridView = (GridView)rootView.findViewById(R.id.imageGridView);
        imageGridView.setAdapter(PicturesActivity.adapter);

        addPictureButton = (Button)rootView.findViewById(R.id.AddPictureButton);
        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog =  createDialog();
                dialog.show();
            }
        });
        return rootView;
    }

    private AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Add the buttons
        builder.setPositiveButton(getString(R.string.fromCamera), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                aCallback.onTakePictureButtonClicked();
            }
        });
        builder.setNegativeButton(getString(R.string.fromGallery), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                aCallback.onChoosePictureButtonClicked();
            }
        });

        builder.setTitle(getString(R.string.dialogChoose));
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public interface OnPicturesFragmentInteraction{
        public void onTakePictureButtonClicked();
        public void onChoosePictureButtonClicked();
    }
}
