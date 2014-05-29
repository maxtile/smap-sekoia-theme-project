package com.example.sekoia.app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PicturesActivity extends FragmentActivity
        implements PicturesFragment.OnPicturesFragmentInteraction{

    static final String SAVE_CURRENT_PHOTO_PATH = "saveCuPhPa";
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_CHOOSE_PICTURE = 2;
    String mCurrentPhotoPath;

    //public static List<ImageView> ImageList = new ArrayList<ImageView>();
    public static CustomGridAdapter adapter;
    public static List<Bitmap> Bitmaps = new ArrayList<Bitmap>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmapImage = null;

        if(mCurrentPhotoPath == null){
            Toast.makeText(getApplicationContext(), "Error while processing picture", Toast.LENGTH_SHORT);
            return;
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //1. Add the new picture to gallery
            galleryAddPic();

            //2. Create Bitmap for Application
            try {
                 bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                         Uri.parse(mCurrentPhotoPath));
            } catch (IOException e) {
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

            bitmapImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
        }
        //Todo: Sync with server in some background task.
        //Todo: When synced:
        //Todo: Add bitmap to some sort of list, and add the photoPath to the Realative...
        Bitmaps.add(bitmapImage);
        adapter.notifyDataSetChanged();
        //Todo: After adding to some bitmap list, the fragment needs to be refreshed.
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
            mCurrentPhotoPath = savedInstanceState.getString(SAVE_CURRENT_PHOTO_PATH, null);
        }
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

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
