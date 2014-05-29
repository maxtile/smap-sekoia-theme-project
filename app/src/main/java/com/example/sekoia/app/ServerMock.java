package com.example.sekoia.app;

import android.app.Activity;
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
public class ServerMock extends Activity implements IServerInteraction {
    //As this is a Mock the Server URL is set to Local Media storage
    private String URL = ""; //Todo - exteralFilesDir must be inserted i think.


    @Override
    public List<Bitmap> GetPictureThumbnails(List<String> filenames) {
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for(String filename : filenames){
            try{
                String pathToFile = URL + "/" + filename;
                File file = new File(pathToFile);
                if(file.exists()){
                    Bitmap image = ThumbnailUtils.extractThumbnail(
                            BitmapFactory.decodeFile(pathToFile), 64, 64);
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
    public int UploadImage(File imageFile) {
        //StartFakeUploadService here.
        return 0;
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
