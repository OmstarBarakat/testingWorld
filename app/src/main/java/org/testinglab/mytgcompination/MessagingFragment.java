package org.testinglab.mytgcompination;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.testinglab.mytgcompination.service_receiver.MessageLogger;
import org.testinglab.mytgcompination.service_receiver.MessagingService;

/**
 * Created by Omstar on 02/03/2016.
 */
public class MessagingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MessagingFragment.class.getSimpleName();

    private Button mSendUniChat;
    private Button mSendDuoChats;
    private Button mSendRushChat;
    private TextView mDataPortView;
    private Button mClearLogBtn;

    private Messenger mService;
    private boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
            setButtonsState(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound = false;
            setButtonsState(false);
        }
    };

    private SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (MessageLogger.LOG_KEY.equals(key)) {
                        mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
                    }
                }
            };

    public MessagingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notify_me, container, false);

        mSendUniChat = (Button) rootView.findViewById(R.id.send_uni_chat);
        mSendUniChat.setOnClickListener(this);

        mSendDuoChats = (Button) rootView.findViewById(R.id.send_duo_chats);
        mSendDuoChats.setOnClickListener(this);

        mSendRushChat =
                (Button) rootView.findViewById(R.id.send_rush_chat);
        mSendRushChat.setOnClickListener(this);

        mDataPortView = (TextView) rootView.findViewById(R.id.data_port);
        mDataPortView.setMovementMethod(new ScrollingMovementMethod());

        mClearLogBtn = (Button) rootView.findViewById(R.id.clear);
        mClearLogBtn.setOnClickListener(this);

        setButtonsState(false);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view == mSendUniChat) {
            sendMsg(1, 1);
        } else if (view == mSendDuoChats) {
            sendMsg(2, 1);
        } else if (view == mSendRushChat) {
            sendMsg(1, 3);
        } else if (view == mClearLogBtn) {
            MessageLogger.clear(getActivity());
            mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().bindService(new Intent(getActivity(), MessagingService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MessageLogger.getPrefs(getActivity()).unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataPortView.setText(MessageLogger.getAllMessages(getActivity()));
        MessageLogger.getPrefs(getActivity()).registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    private void sendMsg(int howManyConversations, int messagesPerConversation) {
        if (mBound) {
            Message msg = Message.obtain(null, MessagingService.MSG_SEND_NOTIFICATION,
                    howManyConversations, messagesPerConversation);
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                Log.e(TAG, "Error sending a message", e);
                MessageLogger.logMessage(getActivity(), "Error occurred while sending a message.");
            }
        }
    }

    private void setButtonsState(boolean enable) {
        mSendUniChat.setEnabled(enable);
        mSendDuoChats.setEnabled(enable);
        mSendRushChat.setEnabled(enable);
    }

}
