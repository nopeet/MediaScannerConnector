package com.nopeet.mediascannerconnector;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
        super(MainActivity.class);
    }

    private MainActivity mActivity;
    private Button mButton;
    private EditText mEditText;
    private TextView mTextView;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mButton = (Button)mActivity.findViewById(R.id.button);
        mTextView = (TextView)mActivity.findViewById(R.id.textView);
        mEditText = (EditText)mActivity.findViewById(R.id.editText);
    }

    public void testWrongPath() {
        TouchUtils.clickView(this, mEditText);
        sendKeys("SLASH S D C A R D SLASH W R O N G SLASH T E S T PERIOD M P 3");
        TouchUtils.clickView(this, mButton);
        Log.d("ApplicationTest", "mTextView : \n" + mTextView.getText());
        assertTrue(mTextView.getText().toString().endsWith("Fail\n"));
    }

    public void testCorrectPath() {
        //TouchUtils.clickView(this, mEditText);
        //sendKeys("SLASH S D C A R D SLASH SHIFT_LEFT D O W N L O A D SLASH T E S T PERIOD M P 3");
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        mEditText.setText("/sdcard/Download/test.mp3");
                    }
                }
        );
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, mButton);
        Log.d("ApplicationTest", "mTextView : \n" + mTextView.getText());
        assertTrue(mTextView.getText().toString().endsWith("Success\n"));
    }
}