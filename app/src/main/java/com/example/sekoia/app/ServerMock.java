package com.example.sekoia.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;

import com.example.sekoia.app.Interfaces.IServerInteraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxi on 29-05-14.
 */
public class ServerMock implements IServerInteraction {
    //As this is a Mock the Server URL is set to Local Media storage
    private String URL = ""; //Todo - exteralFilesDir must be inserted i think.
    public final static String IMAGE_FILE_PATH_TAG = "ifp";
    private Context context;

    public ServerMock (Context context){
        this.context = context.getApplicationContext();
        URL = context.getExternalFilesDir(null).getAbsolutePath();
    }

    @Override
    public List<Bitmap> GetPictureThumbnails(List<String> filenames) {
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for(String filename : filenames){
            try{
                String pathToFile = URL + "/" + filename;
                File file = new File(pathToFile);
                if(file.exists()){
                    Bitmap image = ThumbnailUtils.extractThumbnail(
                            BitmapFactory.decodeFile(pathToFile), PicturesActivity.THUMBNAIL_SIZE,
                            PicturesActivity.THUMBNAIL_SIZE);
                    bitmaps.add(image);
                }
                else{
                    Log.w(this.toString(), "" + pathToFile + " does not exist!");
                }
            }catch (Exception exception){
                Log.e(this.toString(), "Could not create bitmap!");
            }
        }
        return bitmaps;
    }

    @Override
    public int UploadImage(String fullFilePath) {
        try{
            Log.d(this.toString(), "Starting upload service");
            Intent serviceIntent = new Intent(context, FakeUploadService.class);
            serviceIntent.putExtra(IMAGE_FILE_PATH_TAG, fullFilePath);
            context.startService(serviceIntent);
        }catch (Exception e){
            Log.e(this.toString(), "Error while trying to start upload service");
            e.printStackTrace();
            return FAILURE;
        }
        return SUCCESS;
    }

    //Todo: Uploading image will be the same as saving local in tis application.
   /* Log.d(this.toString(), "Uploading: " + imageFile.getAbsolutePath() + "");
    new Thread(new Runnable() {
        @Override
        public void run() {
            Log.d(this.toString(), "sleep....");
            SystemClock.sleep(3000);
            Log.d(this.toString(), "awaaaaake...");
        }
    }).start();
    return SUCCESS;*/
}
