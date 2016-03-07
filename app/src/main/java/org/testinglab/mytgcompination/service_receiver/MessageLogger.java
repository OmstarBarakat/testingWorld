
package org.testinglab.mytgcompination.service_receiver;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

// This class use a shared preferences to log messages, their reads and replies. (NOT REQUIRED)

public class MessageLogger {

    private static final String PREF_MESSAGE = "MESSAGE_LOGGER";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String LOG_KEY = "message_data";
    public static final String LINE_BREAKS = "\n\n";

    public static void logMessage(Context context, String message) {
        SharedPreferences prefs = getPrefs(context);
        message = DATE_FORMAT.format(new Date(System.currentTimeMillis())) + ": " + message;
        prefs.edit()
                .putString(LOG_KEY, prefs.getString(LOG_KEY, "") + LINE_BREAKS + message)
                .apply();
    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_MESSAGE, Context.MODE_PRIVATE);
    }

    public static String getAllMessages(Context context) {
        return getPrefs(context).getString(LOG_KEY, "");
    }

    public static void clear(Context context) {
        getPrefs(context).edit().remove(LOG_KEY).apply();
    }
}
