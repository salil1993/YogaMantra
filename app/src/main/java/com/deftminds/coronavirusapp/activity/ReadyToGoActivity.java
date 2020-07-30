package com.deftminds.coronavirusapp.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.util.LocalEventFromMainActivity;
import com.deftminds.coronavirusapp.util.LocalEventFromMediaPlayerHolder;
import com.deftminds.coronavirusapp.util.MediaPlayerHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ReadyToGoActivity extends AppCompatActivity  {


    private static final String TAG = "ReadyToGoActivity";

    int [] songs;
    TextView mTextDebug;
    Button mPlayButton;
    Button mPauseButton;
    Button mResetButton;
    SeekBar mSeekbarAudio;
    Integer count =1;
    private CountDownTimer mCountDownTimer;
    private int i=0;
    private MediaPlayer mp;
    private String time,description,pose,image;
    private ImageView iv_img;
    private TextView tv_pose,time_left,title;
    private ImageView pause, play,iv_back;
    private MediaPlayerHolder mMediaPlayerHolder;
    private boolean isUserSeeking;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_togo);

        /*Getting Intent from Countdown Activity*/
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        description = intent.getStringExtra("description");
        pose = intent.getStringExtra("pose");
        image = intent.getStringExtra("image");
        title = (TextView)findViewById(R.id.title);
        title.setText(pose);
        iv_img=findViewById(R.id.iv_img);
        Glide.with(ReadyToGoActivity.this )
                .load(image)
                .fitCenter()
                .into(iv_img);

        bindViews();
        songs= new int[] {R.raw.healing_water};
        EventBus.getDefault().register(this);
        mMediaPlayerHolder = new MediaPlayerHolder(this);
        setupSeekbar();

        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        stop();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayerHolder.release();
    }

    private void bindViews() {
        mPauseButton = findViewById(R.id.iv_pause);
        mPlayButton = findViewById(R.id.iv_play);
        mSeekbarAudio = findViewById(R.id.seekbar_audio);
        progressBar = (ProgressBar) findViewById(R.id.step_pose_progress);
        progressBar.setMax(Integer.parseInt(time));
        count =1;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        new MyTask().execute(Integer.valueOf(time));
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
                mPlayButton.setVisibility(View.VISIBLE);
                mPauseButton.setVisibility(View.INVISIBLE);
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                mPlayButton.setVisibility(View.INVISIBLE);
                mPauseButton.setVisibility(View.VISIBLE);
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        mMediaPlayerHolder.load(songs[0]);
        //step_pose_progress=findViewById(R.id.step_pose_progress);
      //  step_pose_progress.setCurrentProgress(i);
        play();
        time_left=findViewById(R.id.time_left);


     //   counteractive();
    }


    public void setupSeekbar() {
        mSeekbarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // This holds the progress value for onStopTrackingTouch.
            int userSelectedPosition = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Only fire seekTo() calls when user stops the touch event.
                if (fromUser) {
                    userSelectedPosition = progress;
                    isUserSeeking = true;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserSeeking = false;
                EventBus.getDefault().post(new LocalEventFromMainActivity.SeekTo(
                        userSelectedPosition));
            }
        });
    }

    // Handle user input for button presses.

    void pause() {
        EventBus.getDefault().post(new LocalEventFromMainActivity.PausePlayback());
    }

    void play() {
        EventBus.getDefault().post(new LocalEventFromMainActivity.StartPlayback());
    }

    void reset() {
        EventBus.getDefault().post(new LocalEventFromMainActivity.ResetPlayback());
    }
    void stop() {
        EventBus.getDefault().post(new LocalEventFromMainActivity.StopPlayback());
    }
    // Display log messges to the UI.

    public void log(StringBuffer formattedMessage) {
        if (mTextDebug != null) {
            mTextDebug.setText(formattedMessage);
            // Move the mScrollContainer focus to the end.

        } else {
            Log.d(TAG, String.format("log: %s", formattedMessage));
        }
    }

    // Event subscribers.
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocalEventFromMediaPlayerHolder.UpdateLog event) {
        log(event.formattedMessage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocalEventFromMediaPlayerHolder.PlaybackDuration event) {
        mSeekbarAudio.setMax(event.duration);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocalEventFromMediaPlayerHolder.PlaybackPosition event) {
        if (!isUserSeeking) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mSeekbarAudio.setProgress(event.position, true);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocalEventFromMediaPlayerHolder.StateChanged event) {
       // Toast.makeText(this, String.format("State changed to:%s", event.currentState), Toast.LENGTH_SHORT).show();
    }


    private class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
          //  txt.setText(result);
            time_left.setText(time+"/"+time);
            stop();
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
          //  btn.setText("Restart");
        }
        @Override
        protected void onPreExecute() {
           // txt.setText("Task Starting...");
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
          //  txt.setText("Running..."+ values[0]);
            time_left.setText(values[0]+"/"+time);
            progressBar.setProgress(values[0]);
        }
    }
}
