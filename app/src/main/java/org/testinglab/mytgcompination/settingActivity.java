package org.testinglab.mytgcompination;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by omstar on 01/03/2016.
 */
public class settingActivity extends PreferenceActivity {


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings);
    }
}
