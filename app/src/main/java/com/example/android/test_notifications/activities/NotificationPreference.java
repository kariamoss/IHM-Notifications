package com.example.android.test_notifications.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.test_notifications.R;
import com.example.android.test_notifications.fragments.PreferenceEventType;
import com.example.android.test_notifications.fragments.PreferenceStoreType;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Jehan on 16/05/2017.
 */

public class NotificationPreference extends PreferenceActivity {


    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return PreferenceEventType.class.getName().equals(fragmentName) || PreferenceStoreType.class.getName().equals(fragmentName);
    }

    @Override
    public void onStop(){
        FirebaseMessaging mFirebaseMessaging = FirebaseMessaging.getInstance();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(prefs.getBoolean(entry.getKey(), true)){
                mFirebaseMessaging.subscribeToTopic(entry.getKey());
                Log.d(TAG, "Subscribed to topic : " + entry.getKey());
            }
            else{
                mFirebaseMessaging.unsubscribeFromTopic(entry.getKey());
                Log.d(TAG, "Unsubscribed to topic : " + entry.getKey());
            }
        }
        super.onStop();
    }
}