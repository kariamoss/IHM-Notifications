package com.example.android.test_notifications.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.example.android.test_notifications.R;


/**
 * Created by Jehan on 16/05/2017.
 */

public class PreferenceEventType extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_fragment_event_type);
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
