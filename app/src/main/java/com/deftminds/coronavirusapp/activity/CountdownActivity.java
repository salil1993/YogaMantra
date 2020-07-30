package com.deftminds.coronavirusapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deftminds.coronavirusapp.R;

import ticker.views.com.ticker.widgets.circular.timer.callbacks.CircularViewCallback;
import ticker.views.com.ticker.widgets.circular.timer.view.CircularView;

public class CountdownActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView pause, resume,iv_back;
    private CircularView circularViewWithTimer;
    private boolean isPause = false;
    private String time, description, pose, image;
    private TextView tv_skip, tv_pose_details,title;
    int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        Intent i = getIntent();
        time = i.getStringExtra("time");
        description = i.getStringExtra("description");
        pose = i.getStringExtra("pose");
        image = i.getStringExtra("image");
        title = (TextView)findViewById(R.id.title);
        title.setText("YogaAsana");
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

    }

    private void init() {
        circularViewWithTimer = findViewById(R.id.circular_view_with_custom_text);
        CircularView.OptionsBuilder builderWithTimer = new
                CircularView.OptionsBuilder()
                .shouldDisplayText(true)
                .setCounterInSeconds(10)
                .setCircularViewCallback(new CircularViewCallback() {
                    @Override
                    public void onTimerFinish() {
                        validate();
                        //  Toast.makeText(CountdownActivity.this, "CircularCallback: Timer Finished ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onTimerCancelled() {
                        Toast.makeText(CountdownActivity.this, "CircularCallback: Timer Cancelled ", Toast.LENGTH_SHORT).show();
                    }
                });
        circularViewWithTimer.setOptions(builderWithTimer);

        tv_pose_details=findViewById(R.id.tv_pose_details);
        pause = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);
        tv_skip = findViewById(R.id.tv_skip);

        tv_pose_details.setText(description);
        circularViewWithTimer.startTimer();

        pause.setOnClickListener(this);
        resume.setOnClickListener(this);
        tv_skip.setOnClickListener(this);

        resume.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
    }

    private void validate() {
        if (i==0) {
            Intent i = new Intent(CountdownActivity.this, ReadyToGoActivity.class);
            i.putExtra("time", time);
            i.putExtra("description", description);
            i.putExtra("pose", pose);
            i.putExtra("image", image);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_skip:
                i=1;
                Intent intent = new Intent(getApplicationContext(), ReadyToGoActivity.class);
                intent.putExtra("time", time);
                intent.putExtra("description", description);
                intent.putExtra("pose", pose);
                intent.putExtra("image", image);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.pause:
                if (circularViewWithTimer.pauseTimer()) {
                    isPause = true;
                    resume.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);

                } else {

                    Toast.makeText(this, "Timer finished before pausing", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.resume:

                if (isPause) {
                    resume.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    circularViewWithTimer.resumeTimer();
                    isPause = false;

                } else {
                    Toast.makeText(this, "Timer Not Paused before", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }
}
