package org.testinglab.mytgcompination;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.testinglab.mytgcompination.HQ.HttpManager;
import org.testinglab.mytgcompination.Models.dataItems;
import org.testinglab.mytgcompination.Parsers.FlowerXMLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omstar on 01/03/2016.
 */
public class FeedNewsActivity extends ActionBarActivity {

    @SuppressWarnings("UnusedDeclaration")
    public static final String PHOTOS_BASE_URL =
            "http://services.hanselandpetal.com/photos/";

    ProgressBar pb;
    private ListView lv;
    List<MyTask> lTasks;

    List<dataItems> flowerList;
    private static final String LOGTAG = "TST";
    private static final String USERNAME = "feeduser";
    private static final String PASSWORD = "feedpassword";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feednews);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        lTasks = new ArrayList<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feednews, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_doTask_xml) {
            if (isOnline()) {
                requestData("http://services.hanselandpetal.com/secure/flowers.xml");
            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {
        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        lv = (ListView) findViewById(R.id.list_feed);
        lv.setAdapter(adapter);
    }

    private class MyTask extends AsyncTask<String, String, List<dataItems>> {

        @Override
        protected void onPreExecute() {
            //starting task
            if (lTasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            lTasks.add(this);
        }

        @Override
        protected List<dataItems> doInBackground(String... params) {

            String content = HttpManager.getData(params[0], USERNAME, PASSWORD);

            if (content != null) {
                flowerList = FlowerXMLParser.parseFeed(content);

            } else {
                Log.i(LOGTAG, "content is null");//
                flowerList = null;
            }

            return flowerList;
        }

        @Override
        protected void onPostExecute(List<dataItems> result) {

            lTasks.remove(this);
            if (lTasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);

            }

            if (result == null) {
                Toast.makeText(FeedNewsActivity.this, "Check your connection, NO CONTENT FOUND!", Toast.LENGTH_LONG).show();
                return;
            }

            flowerList = result;
            updateDisplay();


        }

        @Override
        protected void onProgressUpdate(String... values) {

        }
    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "This app connected via wifi", Toast.LENGTH_LONG).show();
            } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                Toast.makeText(this, "This app connected via the Ethernet network", Toast.LENGTH_LONG).show();
            }
            return true;
        } else {
            return false;
        }
    }

}

