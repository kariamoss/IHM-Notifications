package com.example.android.test_notifications.activities;

import android.preference.PreferenceActivity;

import com.example.android.test_notifications.R;
import com.example.android.test_notifications.fragments.PreferenceEventType;
import com.example.android.test_notifications.fragments.PreferenceStoreType;

import java.util.List;

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
}