package org.testinglab.mytgcompination;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Omstar on 02/03/2016.
 */
public class NotifyServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_service);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MessagingFragment())
                    .commit();
        }
    }
}
