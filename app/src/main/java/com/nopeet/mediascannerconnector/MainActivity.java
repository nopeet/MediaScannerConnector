package com.nopeet.mediascannerconnector;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.media.MediaScannerConnection.*;

public class MainActivity extends Activity {
    private static final String TAG = "MediaScannerConnector";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Option menu item selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String mPath;
    private Uri mUri;

    public void onClick(View v) {
        Toast.makeText(MainActivity.this,"Scan button clicked", Toast.LENGTH_SHORT).show();

        OnScanCompletedListener mScanCompletedListener = new OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.d(TAG, "path : " + path);
                Log.d(TAG, "uri : " + uri);
                mPath = path;
                mUri = uri;
                runOnUiThread(
                    new Runnable() {
                        public void run() {
                            TextView textView = (TextView)findViewById(R.id.textView);
                            textView.append("---\n");
                            textView.append("path : " + mPath + "\n");
                            textView.append("uri : " + mUri + "\n");
                            if (mUri == null) {
                                textView.append("=> Fail\n");
                            } else {
                                textView.append("=> Success\n");
                            }
                        }
                    }
                );
            }
        };
        //
        String[] paths = {((EditText)findViewById(R.id.editText)).getText().toString()};
        //String[] mimeTypes = {"image/jpeg"};
        //scanFile(getApplicationContext(), paths, mimeTypes, mScanCompletedListener);
        scanFile(getApplicationContext(), paths, null, mScanCompletedListener);
    }
}
