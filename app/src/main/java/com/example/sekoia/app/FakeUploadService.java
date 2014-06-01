package com.example.sekoia.app;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class FakeUploadService extends Service {

    private Handler handler = new Handler();

    public FakeUploadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fullFilePath = intent.getStringExtra(ServerMock.IMAGE_FILE_PATH_TAG);
        Runnable customRunnable = new MyRunnable(fullFilePath);
        new Thread(customRunnable).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void uploadFile(String fullFilePath){
        File imageFile = new File(fullFilePath);
        Log.d(this.toString(), "Uploading: " + imageFile.getAbsolutePath() + "");
        SystemClock.sleep(5000);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), getString(R.string.UploadFinished),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }

    class MyRunnable implements Runnable{
        private String data;

        MyRunnable(String data){
            this.data = data;
        }
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.pictureUpload),
                            Toast.LENGTH_SHORT).show(); //todo: Lav sådan at der står hvem man uploader til.
                }
            });
            uploadFile(data);
        }
    }
}
