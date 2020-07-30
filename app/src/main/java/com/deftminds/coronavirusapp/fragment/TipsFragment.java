package com.deftminds.coronavirusapp.fragment;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.Feedback;
import com.deftminds.coronavirusapp.activity.MainActivity;
import com.deftminds.coronavirusapp.adapter.BenifitAdapter;
import com.deftminds.coronavirusapp.model.Benefit;
import com.deftminds.coronavirusapp.util.ReminderBroadcast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static com.deftminds.coronavirusapp.fragment.HomeFragment.isNetworkConnected;

public  class TipsFragment extends Fragment  {

    private ImageView iv_img;
    private TextView tv_heading,tv_desc;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        MainActivity.title.setText("Tips");
        tv_desc=root.findViewById(R.id.tv_desc);
        tv_heading=root.findViewById(R.id.tv_heading);
        iv_img=root.findViewById(R.id.iv_img);
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tips");
        return  root;
    }



    @Override
    public void onStart() {
        super.onStart();

        if (isNetworkConnected(getContext())) {
            //attaching value event listener
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

               String title = dataSnapshot.child("title").getValue(String.class);
               String desc = dataSnapshot.child("description").getValue(String.class);
               String image = dataSnapshot.child("image").getValue(String.class);
               tv_heading.setText(title);
               tv_desc.setText(desc);
                    Glide.with(getContext() )
                            .load(image)
                            .centerCrop()
                            .into(iv_img);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("No Internet!")
                    .setMessage("Please Connect with Your Internet!")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

}
