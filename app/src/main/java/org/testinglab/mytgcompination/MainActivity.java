package org.testinglab.mytgcompination;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String prefValue = PREF.getString(USERNAME, "Not Found!");
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
    }

    public void feedListListener(View view) {
        Intent intent = new Intent(MainActivity.this, FeedNewsActivity.class);
        startActivity(intent);
    }
}