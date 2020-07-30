package com.deftminds.coronavirusapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deftminds.coronavirusapp.model.Pose;
import com.deftminds.coronavirusapp.adapter.PoseAdapter;
import com.deftminds.coronavirusapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PoseActivity extends AppCompatActivity {
    private static final String TAG = "PoseActivity";
    private List<Pose> poseArrayList = new ArrayList<>();
    private Query databaseReference;
    private ShimmerRecyclerView recyclerView;
    private TextView title;
    private PoseAdapter poseAdapter;
    private String header;
    private ImageView iv_back;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        Intent intent = getIntent();
        header = intent.getStringExtra("title");
        Log.e(TAG, "onCreate:heading "+header );
        title = (TextView)findViewById(R.id.title);

        title.setText(header);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AdView mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(header);
        recyclerView = (ShimmerRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PoseActivity.this));

    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public void onStart() {
        super.onStart();
        spinner.setVisibility(View.VISIBLE);
        if (isNetworkConnected(PoseActivity.this)) {
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: "+dataSnapshot.toString() );
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        Log.e(TAG, "onDataChange:postSnapshot "+postSnapshot.toString());
                        Pose pose = postSnapshot.getValue(Pose.class);
                        poseArrayList.add(pose);


                }
                spinner.setVisibility(View.GONE);
                poseAdapter = new PoseAdapter(PoseActivity.this,poseArrayList,header);
                recyclerView.setAdapter(poseAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }else {
            spinner.setVisibility(View.GONE);
            new AlertDialog.Builder(PoseActivity.this)
                    .setTitle("No Internet!")
                    .setMessage("Please Connect with Your Internet!")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        poseArrayList.clear();
    }
}