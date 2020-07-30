package com.deftminds.coronavirusapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.adapter.PoseAdapter;
import com.deftminds.coronavirusapp.adapter.YogaAdapter;
import com.deftminds.coronavirusapp.model.Pose;
import com.deftminds.coronavirusapp.model.Yoga;
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

public class Yogasana extends AppCompatActivity {
    private static final String TAG = "YogaActivity";
    private List<Yoga> yogaArrayList = new ArrayList<>();
    private Query databaseReference;
    private ShimmerRecyclerView recyclerView;
    private TextView title;
    private YogaAdapter yogaAdapter;
    private String day;
    private ImageView iv_back;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        title = (TextView)findViewById(R.id.title);
        title.setText(day);
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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("yogasana").child(day);
        recyclerView = (ShimmerRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Yogasana.this));

    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public void onStart() {
        super.onStart();
        spinner.setVisibility(View.VISIBLE);
        if (isNetworkConnected(Yogasana.this)) {
            //attaching value event listener
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e(TAG, "onDataChange: "+dataSnapshot.toString() );
                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "onDataChange:postSnapshot "+postSnapshot.toString());
                        Yoga yoga = postSnapshot.getValue(Yoga.class);
                        yogaArrayList.add(yoga);
                    }
                    spinner.setVisibility(View.GONE);
                    yogaAdapter = new YogaAdapter(Yogasana.this,yogaArrayList);
                    recyclerView.setAdapter(yogaAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            spinner.setVisibility(View.GONE);
            new AlertDialog.Builder(Yogasana.this)
                    .setTitle("No Internet!")
                    .setMessage("Please Connect with Your Internet!")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        yogaArrayList.clear();
    }
}
