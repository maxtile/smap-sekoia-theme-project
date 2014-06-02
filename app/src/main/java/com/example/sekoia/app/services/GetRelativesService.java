package com.example.sekoia.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.example.sekoia.app.models.Relative;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class GetRelativesService extends Service {

    public static final String REST_SERVER_ENDPOINT = "http://demo3067547.mockable.io/";
    public static final String NOTIFICATION = "receiver";
    public static final String RESULT = "result";
    public static final int RESULT_OK = 94215;
    public static final int RESULT_FAILED = 2366316;

    private List<Relative> mList;
    private final IBinder mBinder = new IProtocolBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class IProtocolBinder extends Binder {
        public GetRelativesService getService() {
            return GetRelativesService.this;
        }
    }

    public void startDownload() {
        new DownloadTask().execute();
    }

    public List<Relative> getData() {
        return mList;
    }

    private void notifyDownloadDone(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    private class DownloadTask extends AsyncTask<Void, Integer, List<Relative>> {

        @Override
        protected List<Relative> doInBackground(Void... args) {
            String json = getJSONRaw();
            if(json == null) return null;
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Relative>>() {}.getType();
            List<Relative> results = gson.fromJson(json, listType);
            return results;
        }

        protected void onPostExecute(List<Relative> result) {
            if(result == null) {
                notifyDownloadDone(RESULT_FAILED);
            }else{
                mList = result;
                notifyDownloadDone(RESULT_OK);
            }
        }

        public String getJSONRaw() {
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(REST_SERVER_ENDPOINT);
            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    return null;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }
    }



}
