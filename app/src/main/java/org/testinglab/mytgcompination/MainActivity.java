package org.testinglab.mytgcompination;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.testinglab.mytgcompination.Utils.UIHelper;


public class MainActivity extends ActionBarActivity {

    public static final String USERNAME = "pref_username";
    public static final String VIEWIMAGE = "pref_viewimages";
    private SharedPreferences PREF;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    private static final String TAG = "TEST";
    private static final String USER_FEED = "nickname";
    private static final String PASSWORD_FEED = "ticket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate main Activity");

        PREF = PreferenceManager.getDefaultSharedPreferences(this);

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) throws NullPointerException {
                Toast.makeText(MainActivity.this, "Settings saved!", Toast.LENGTH_SHORT).show();
                MainActivity.this.refreshDisplay(null);
            }
        };
        PREF.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart main Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume main Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop main Activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.user_settings:
                setPreference();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshDisplay(View view) {
        String prefValue = PREF.getString(USERNAME, "Not Found! new user");
        Boolean prefFlag = PREF.getBoolean(VIEWIMAGE, false);

        UIHelper.displayText(this, R.id.tv_username, prefValue);
        UIHelper.setCBFlag(this, R.id.checkbox_viewimage, prefFlag);
    }

    private void setPreference() {

        Intent intent = new Intent(MainActivity.this, settingActivity.class);
        startActivity(intent);
    }


    public void webLinkListener(View view) {
        Uri webPage = Uri.parse("http://www.beirutairport.gov.lb/_weather.php");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(webIntent);
    }

    public void webtravelListener(View view) {
        Uri webPage = Uri.parse("http://www.beirutairport.gov.lb/_flight.php");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(webIntent);
    }

    public void testServiceListener(View view) {
        Intent intent = new Intent(MainActivity.this, NotifyServiceActivity.class);
        startActivity(intent);
    }

    public void feedListListener(View view) {
        Intent intent = new Intent(MainActivity.this, FeedNewsActivity.class);
        String userName = UIHelper.getText(this, R.id.fld_name_feed);
        String password = UIHelper.getText(this, R.id.fld_password_feed);
        intent.putExtra(USER_FEED, userName);
        intent.putExtra(PASSWORD_FEED, password);
        startActivity(intent);
    }
}
