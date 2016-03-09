
package org.testinglab.mytgcompination.service_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageReadReceiver extends BroadcastReceiver {
    private static final String TAG = "simple";

    private static final String CONVERSATION_ID = "conversation_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        int conversationId = intent.getIntExtra(CONVERSATION_ID, -1);
        if (conversationId != -1) {
            Log.d(TAG, "Conversation " + conversationId + " was read");
            MessageLogger.logMessage(context, "Conversation " + conversationId + " was read.");
        }
    }
}
