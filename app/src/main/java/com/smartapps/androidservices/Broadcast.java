package com.smartapps.androidservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by priya on 4/4/2017.
 */

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("FILES_DOWNLOADED")) {
            if(intent.getIntExtra("downloadedBytes", 0) > 0) {
                Toast.makeText(context, "Files Download Complete.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, "Download Failed.", Toast.LENGTH_LONG).show();
            }

        }
        else if(intent.getAction().equals("DOWNLOAD_PROGRESS")) {
            Log.d("PM", "" +intent.getIntExtra("percentComplete", 0));
            Toast.makeText(context, intent.getIntExtra("percentComplete", 0) +"% complete.", Toast.LENGTH_SHORT).show();
        }
    }
}