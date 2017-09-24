package com.smartapps.androidservices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by priya on 4/4/2017.
 */

public class Background_download extends IntentService{

    private Intent broadcastIntent = new Intent();

    public Background_download() {

        super("Background_download");
    }

    protected void onHandleIntent(Intent intent) {
        String urls[] = intent.getStringArrayExtra("urls");
        int downloadedSize = DownloadFiles(urls);
        Log.d("PM", "Downloaded " + downloadedSize + " bytes");

        broadcastIntent.setAction("FILES_DOWNLOADED");
        broadcastIntent.putExtra("downloadedBytes", downloadedSize);
        getBaseContext().sendBroadcast(broadcastIntent);
    }



    private int DownloadFiles(String[] urls) {
        int downloadedSize = 0;
        try {
            URL url;
            File file;
            int byteMaxLength = 1024*1024;

            for (int i = 0; i < urls.length; i++) {
                url = new URL(urls[i]);
                file = new File(getApplicationContext().getFilesDir(), urls[i].substring(urls[i].lastIndexOf("/")+1));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file);
                downloadedSize += connection.getContentLength();

                byte[] buffer = new byte[byteMaxLength];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer)) > 0 ){
                    outputStream.write(buffer, 0, bufferLength);
                }
                inputStream.close();
                outputStream.close();

                broadcastIntent.setAction("DOWNLOAD_PROGRESS");
                broadcastIntent.putExtra("percentComplete", ((i+1)*100)/urls.length);
                getBaseContext().sendBroadcast(broadcastIntent);
            }
        }
        catch (Exception e) {
            Log.d("PM", e.getMessage());
            return 0;
        }

        return downloadedSize;
    }



}

