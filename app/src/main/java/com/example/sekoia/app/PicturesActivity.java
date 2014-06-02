package com.example.sekoia.app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sekoia.app.interfaces.IServerInteraction;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PicturesActivity extends FragmentActivity
        implements PicturesFragment.OnPicturesFragmentInteraction{
    //private static List<Object> selectedImages;

    static final int THUMBNAIL_SIZE = 400;
    static final String SAVE_CURRENT_PHOTO_PATH = "saveCuPhPa";
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_CHOOSE_PICTURE = 2;
    String mCurrentPhotoPath;
    String mCurrentPhotoFileName;
    IServerInteraction serverInteraction;

    public static CustomGridAdapter adapter;
    public static List<Bitmap> Bitmaps = new ArrayList<Bitmap>();

    public PicturesActivity(IServerInteraction serverInteraction){
        this.serverInteraction = serverInteraction;
    }

    public PicturesActivity(){
        //Default constructor
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmapImage = null;

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if(mCurrentPhotoPath == null){
                Toast.makeText(getApplicationContext(), getString(R.string.PictureProcessError),
                        Toast.LENGTH_LONG);
                return;
            }

            try {
                mCurrentPhotoPath = Uri.parse(mCurrentPhotoPath).getPath();
                 bitmapImage = ThumbnailUtils.extractThumbnail(
                         BitmapFactory.decodeFile(mCurrentPhotoPath),
                         THUMBNAIL_SIZE, THUMBNAIL_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(requestCode == REQUEST_CHOOSE_PICTURE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mCurrentPhotoPath = cursor.getString(columnIndex);
            cursor.close();

            bitmapImage = ThumbnailUtils.extractThumbnail(
                    BitmapFactory.decodeFile(mCurrentPhotoPath)
                    , THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        }

        //Todo: add the photoPath to the Realative on database
        //Todo: add Background task where we upload real picture file.
        serverInteraction.UploadImage(mCurrentPhotoPath);
        Bitmaps.add(bitmapImage);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PicturesFragment())
                    .commit();
        }
        else{
            if (Build.VERSION.SDK_INT >= 11){
            mCurrentPhotoPath = savedInstanceState.getString(SAVE_CURRENT_PHOTO_PATH, null);
            }
        }
        this.serverInteraction = new ServerMock(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVE_CURRENT_PHOTO_PATH, mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pictures, menu);
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

    @Override
    public void onTakePictureButtonClicked() {
        dispatchTakePictureIntent();
    }

    @Override
    public void onChoosePictureButtonClicked() {
        dispatchChoosePictureIntent();
    }

    @Override
    public void onDeletePictureButtonClicked() {
        dispatchDeletePictureButton();
    }

    private void dispatchDeletePictureButton() {
        /*GridView gridView = (GridView) findViewById(R.id.imageGridView);
        if (adapter.getItem(gridView.getSelectedItemPosition()) != null )
        {
            Bitmaps.remove(adapter.getItem(gridView.getSelectedItemPosition()));
        }
        */
        adapter.notifyDataSetChanged();

    }

    private void dispatchChoosePictureIntent() {
        Intent choosePictureIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(choosePictureIntent, REQUEST_CHOOSE_PICTURE);
    }

    // Code comes from http://developer.android.com/training/camera/photobasics.html
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        else {
            Log.e(this.toString(), "No camera activity found to handle the intent!");
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save name to for storage in database on current Relative.
        mCurrentPhotoPath = image.getName();
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
