package com.example.android.test_notifications.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.test_notifications.R;
import com.example.android.test_notifications.adapters.NotificationAdapter;
import com.example.android.test_notifications.models.Notification;
import com.google.common.collect.Iterators;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Jehan on 10/05/2017.
 */

public class PersonalNotifications extends Fragment {
    private List<Notification> mNotificationList;
    private List<Notification> mTotalNotificationList;
    private LinearLayoutManager layoutManager;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private NotificationAdapter mNotificationAdapter;
    AVLoadingIndicatorView avLoadingIndicatorView;

    public PersonalNotifications() {
        mNotificationList = new ArrayList<>();
        mTotalNotificationList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this.getContext());
        mNotificationAdapter = new NotificationAdapter(mNotificationList);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PersonalNotifications newInstanceGrid() {
        return new PersonalNotifications();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("notifications");


        if(mChildEventListener == null){
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    if(notificationRequiredByUser(notification)){
                        mNotificationList.add(0, notification);
                        mNotificationAdapter.notifyItemInserted(mNotificationList.size() -1);
                    }
                    mTotalNotificationList.add(0, notification);
                    avLoadingIndicatorView.hide();
                }
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
            };
            mMessageDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    public void onResume(){
        ListIterator<Notification> notificationIterator = mTotalNotificationList.listIterator();

        //TODO Afficher les nouvelles notifications en changeant de paramètres
        while (notificationIterator.hasNext()) {
            Notification notif = notificationIterator.next();
            if(notificationRequiredByUser(notif) && !mNotificationList.contains(notif)){
                notificationIterator.add(notif);
                mNotificationAdapter.notifyItemInserted(Iterators.size(notificationIterator) -1);
            }
            Log.d(TAG, "For " + notif.getTitle() + "/!\\ notifRequir : " + notificationRequiredByUser(notif) + ". contains : " + mNotificationList.contains(notif));
        }

        ListIterator<Notification> notificationListIterator = mNotificationList.listIterator();
        while (notificationListIterator.hasNext()) {
            Notification notif = notificationListIterator.next();
            if(!notificationRequiredByUser(notif) && mNotificationList.contains(notif)){
                notificationListIterator.remove();
                mNotificationAdapter.notifyDataSetChanged();
            }
        }

        super.onResume();
    }

    private boolean notificationRequiredByUser(Notification notification) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean haveCat = prefs.getBoolean(notification.getCategory().name(), false);
        boolean haveStore1 = true;
        boolean haveStore2 = true;
        if(notification.getStore() != null) haveStore1 = prefs.getBoolean(notification.getStore().name(), false);
        if(notification.getSecondStore() != null) haveStore2 = prefs.getBoolean(notification.getSecondStore().name(), false);

        return haveCat && haveStore1 || haveCat && haveStore2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.section_recycler_label);
        final FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mNotificationAdapter);

        //Loading indicator
        avLoadingIndicatorView = (AVLoadingIndicatorView) getView().findViewById(R.id.loading_indicator);
        avLoadingIndicatorView.show();

        //Make the button disappear when scrolling down
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });
    }
}
