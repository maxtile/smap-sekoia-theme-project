package com.example.sekoia.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by Maximilian on 25.05.14.
 */
public class PicturesFragment extends Fragment {
    private OnPicturesFragmentInteraction aCallback;
    private Button addPictureButton, deletePictureButton;
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
        //imageGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        //imageGridView.setOnTouchListener();
        /*imageGridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if (menuItem != null)
                {
                    aCallback.onGridViewItemClicked();
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });*/

        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                aCallback.onGridViewItemClicked(i);
            }
        });

        addPictureButton = (Button)rootView.findViewById(R.id.AddPictureButton);
        deletePictureButton = (Button)rootView.findViewById(R.id.DeletePictureButton);
        deletePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aCallback.onDeletePictureButtonClicked();
            }
        });
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
        public void onDeletePictureButtonClicked();
        public void onGridViewItemClicked(int position);
    }
}
