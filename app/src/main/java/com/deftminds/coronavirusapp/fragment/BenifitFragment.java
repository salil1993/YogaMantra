package com.deftminds.coronavirusapp.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.MainActivity;
import com.deftminds.coronavirusapp.adapter.BenifitAdapter;
import com.deftminds.coronavirusapp.model.Benefit;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.deftminds.coronavirusapp.fragment.HomeFragment.isNetworkConnected;

public  class BenifitFragment extends Fragment {
    private static final String TAG = "BenifitFragment";
    private List<Benefit> benefitArrayList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ShimmerRecyclerView recyclerView;
    private TextView title;
    private BenifitAdapter benifitAdapter;
    private ProgressBar spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_benifit, container, false);
        title = (TextView)root.findViewById(R.id.title);
        spinner = (ProgressBar)root.findViewById(R.id.progressBar1);
        MainActivity.title.setText("Benifits");
        AdView mAdView = (AdView)root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Benifits Of Yoga");
        recyclerView = (ShimmerRecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;

    }



    @Override
    public void onStart() {
        super.onStart();
        spinner.setVisibility(View.VISIBLE);
        if (isNetworkConnected(getContext())) {
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: "+dataSnapshot.toString() );
                    //getting artist
                    Benefit benefit = postSnapshot.getValue(Benefit.class);
                    benefitArrayList.add(benefit);
                }
                spinner.setVisibility(View.GONE);
                benifitAdapter = new BenifitAdapter(getActivity(),benefitArrayList);
                recyclerView.setAdapter(benifitAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }else {
            spinner.setVisibility(View.GONE);
            new AlertDialog.Builder(getActivity())
                    .setTitle("No Internet!")
                    .setMessage("Please Connect with Your Internet!")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }
}
