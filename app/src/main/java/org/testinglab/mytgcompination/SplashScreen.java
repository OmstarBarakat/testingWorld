package org.testinglab.mytgcompination;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by omstar on 01/03/2016.
 */
public class SplashScreen extends Activity {

    private Thread mSplash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SplashScreen sPlash = this;
        setContentView(R.layout.screen_splash);

        mSplash = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);
                    }
                } catch (InterruptedException ex) {
                }

                finish();

                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        };

        mSplash.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplash) {
                mSplash.notifyAll();
            }
        }
        return true;
    }
}
