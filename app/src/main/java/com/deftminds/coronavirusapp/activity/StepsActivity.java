package com.deftminds.coronavirusapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StepsActivity extends AppCompatActivity {
    String text, text1, text2, text3, text4, videourl;
    private static final String TAG = "StepsActivity";
    private TextView title,heading, subheading1, subheading2, subheading3, subheading4;
    private ImageView iv_back,iv_img,iv_play;
    String header, pose,image;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        title = (TextView) findViewById(R.id.title);

        iv_img = (ImageView)findViewById(R.id.iv_img);
        iv_play = (ImageView)findViewById(R.id.iv_play);
        heading = (TextView) findViewById(R.id.heading);
        subheading1 = (TextView) findViewById(R.id.subheading1);
        subheading2 = (TextView) findViewById(R.id.subheading2);
        subheading3 = (TextView) findViewById(R.id.subheading3);
        subheading4 = (TextView) findViewById(R.id.subheading4);
        title.setText("HOW TO DO");
        Intent intent = getIntent();
        pose = intent.getStringExtra("pose");
        header = intent.getStringExtra("header");
        image = intent.getStringExtra("image");
        Log.e(TAG, "onCreate:heading " + header + pose);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(StepsActivity.this )
                .load(image)
                .fitCenter()
                .into(iv_img);
        AdView mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(header).child(pose);

        }



    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNetworkConnected(StepsActivity.this)) {
            //attaching value event listener
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.e(TAG, "onDataChange: " + snapshot.toString());
                    videourl = snapshot.child("videourl").getValue(String.class);
                    text = snapshot.child("heading").getValue(String.class);
                    text1 = snapshot.child("subheading1").getValue(String.class);
                    text2 = snapshot.child("subheading2").getValue(String.class);
                    text3 = snapshot.child("subheading3").getValue(String.class);
                    text4 = snapshot.child("subheading4").getValue(String.class);
                    Log.e(TAG, "onDataChange: " + videourl + text + text1 + text2 + text3 + text4);
                    heading.setText(text);
                    subheading1.setText("\u25CF " + text1);
                    subheading2.setText("\u25CF " + text2);
                    subheading3.setText("\u25CF " + text3);
                    subheading4.setText("\u25CF " + text4);

                    if (TextUtils.isEmpty(videourl)){
                        Toast.makeText(StepsActivity.this, "empty", Toast.LENGTH_SHORT).show();
                    }else {
                        iv_play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(StepsActivity.this, VideoActivity.class);
                                i.putExtra("pose", pose);
                                i.putExtra("header", header);
                                i.putExtra("video", videourl);
                                startActivity(i);
                            }
                        });
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            new AlertDialog.Builder(StepsActivity.this)
                    .setTitle("No Internet!")
                    .setMessage("Please Connect with Your Internet!")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }
}
