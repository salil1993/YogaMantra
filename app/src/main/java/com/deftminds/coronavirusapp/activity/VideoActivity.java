package com.deftminds.coronavirusapp.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.deftminds.coronavirusapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class VideoActivity extends YouTubeBaseActivity {

    private static final String TAG = "VideoActivity";
    String header, pose,videourl;
    // YouTube player view
    private ImageView iv_back;
    private TextView title;
    DatabaseReference databaseReference;
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        title = (TextView) findViewById(R.id.title);
        title.setText("Mentors");
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
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        Intent intent = getIntent();
        pose = intent.getStringExtra("pose");
        header = intent.getStringExtra("header");
        videourl = intent.getStringExtra("video");
        databaseReference = FirebaseDatabase.getInstance().getReference().child(header).child(pose);
        youTubePlayerView.initialize("AIzaSyCPgjaNAEyZDYXr86fvLZ3tZVN6S2Uk_Nw", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.loadVideo(videourl);
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(VideoActivity.this, "" + youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}
