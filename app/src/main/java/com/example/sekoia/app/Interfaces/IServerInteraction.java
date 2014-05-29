package com.example.sekoia.app.Interfaces;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

/**
 * Created by Maxi on 29-05-14.
 */
public interface IServerInteraction {
    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    public List<Bitmap> GetPictureThumbnails(List<String> filenames);
    public int UploadImage(File imageFile);

}
