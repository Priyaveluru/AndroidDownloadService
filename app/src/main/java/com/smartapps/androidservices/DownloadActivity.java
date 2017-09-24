package com.smartapps.androidservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by priya on 4/2/2017.
 */

public class DownloadActivity extends Activity {
    Button download1;
    private int[] viewId = {R.id.pdf1, R.id.pdf2, R.id.pdf3, R.id.pdf4, R.id.pdf5};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_download_activity);
        download1=(Button)findViewById(R.id.downloadxyz);
        download1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Background_download.class);
                EditText editView;
                String[] urls = new String[5];

                for (int i = 0; i < 5; i++) {
                    editView = (EditText) findViewById(viewId[i]);
                    urls[i] = editView.getText().toString().trim();
                }
                intent.putExtra("urls", urls);
                startService(intent);
            }
        });
    }

}
