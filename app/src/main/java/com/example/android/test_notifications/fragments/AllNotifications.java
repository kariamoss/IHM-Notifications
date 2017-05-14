package com.example.android.test_notifications.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.test_notifications.R;
import com.example.android.test_notifications.adapters.NotificationAdapter;
import com.example.android.test_notifications.models.Notification;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.test_notifications.R.id.fab;

/**
 * Created by Jehan on 10/05/2017.
 */

public class AllNotifications extends Fragment {
    private List<Notification> mNotificationList;
    private LinearLayoutManager layoutManager;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private NotificationAdapter mNotificationAdapter;
    AVLoadingIndicatorView avLoadingIndicatorView;

    public AllNotifications() {
        mNotificationList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this.getContext());
        mNotificationAdapter = new NotificationAdapter(mNotificationList);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AllNotifications newInstanceGrid() {
        return new AllNotifications();
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
                    mNotificationList.add(notification);
                    mNotificationAdapter.notifyItemInserted(mNotificationList.size() -1);
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
