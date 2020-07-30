package com.deftminds.coronavirusapp.activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.deftminds.coronavirusapp.fragment.BenifitFragment;
import com.deftminds.coronavirusapp.fragment.HomeFragment;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.fragment.TipsFragment;
import com.deftminds.coronavirusapp.fragment.TrainingFragment;
import com.deftminds.coronavirusapp.util.ReminderBroadcast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mindinventory.midrawer.MIDrawerView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity  {
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation;
    private FrameLayout frameLayout;
    Fragment selectedFragment =null;
    private int slideType = 0;
    private ImageView iv_menu ;
    private TextView nav_training_plan,nav_reminder,nav_restart,nav_share,nav_rate,nav_feedback;
    public static TextView title;
    private MIDrawerView miDrawerView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.homeContainer);
        title = findViewById(R.id.title);
        navigation = (BottomNavigationView) findViewById(R.id.bottomNavBar);
         iv_menu=(ImageView) findViewById(R.id.iv_menu);
        nav_training_plan=(TextView) findViewById(R.id.nav_training_plan);
        nav_reminder=(TextView) findViewById(R.id.nav_reminder);
        nav_share=(TextView) findViewById(R.id.nav_share);
        nav_rate=(TextView) findViewById(R.id.nav_rate);
        nav_feedback=(TextView) findViewById(R.id.nav_feedback);
        iv_menu.setOnClickListener(this::onClick);
        nav_training_plan.setOnClickListener(this::onClick);
        nav_reminder.setOnClickListener(this::onClick);
    //    nav_restart.setOnClickListener(this::onClick);
        nav_share.setOnClickListener(this::onClick);
        nav_rate.setOnClickListener(this::onClick);
        nav_feedback.setOnClickListener(this::onClick);
         miDrawerView=findViewById(R.id.drawer_layout);
        // Set color for the container's content as transparent
        miDrawerView.setScrimColor(Color.TRANSPARENT);
        createNotificationChannel();
        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.action_home); // change to whichever id should be default
        }
        miDrawerView.setMIDrawerListener(new MIDrawerView.MIDrawerEvents() {
            @Override
            public void onDrawerOpened(View view) {

                Log.d("tAG", "Drawer Opened");
            }

            @Override
            public void onDrawerClosed(View view) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        if (miDrawerView.isDrawerOpen(GravityCompat.START)) {
            miDrawerView.closeDrawer(GravityCompat.START);
        }
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            switch (id) {
                case R.id.action_home:
                selectedFragment = new HomeFragment();
                break;

                case R.id.action_benifits:
                    selectedFragment = new BenifitFragment();
                    break;
                case R.id.action_tips:
                    selectedFragment = new TipsFragment();
                    break;

            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeContainer, selectedFragment).addToBackStack(null).commit();
            return true;
        }
    };


    public void onClick(View v) {
      switch (v.getId()){
          case R.id.iv_menu:
              miDrawerView.openDrawer(GravityCompat.START);
              break;

          case R.id.nav_training_plan:
              Fragment fragment = new TrainingFragment();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.homeContainer, fragment).commit();
              miDrawerView.closeDrawer(GravityCompat.START);
              break;

          case R.id.nav_reminder:
              createNotification();
              break;

         /* case R.id.nav_restart:
              break;*/

          case R.id.nav_share:
              Intent sendIntent = new Intent();
              sendIntent.setAction(Intent.ACTION_SEND);
              sendIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.deftminds.coronavirusapp");
              sendIntent.setType("text/plain");
              Intent.createChooser(sendIntent,"Share via");
              startActivity(sendIntent);
              break;

          case R.id.nav_rate:
              AlertDialog.Builder builder = new AlertDialog.Builder(this)
                      .setTitle("Rate application")
                      .setMessage("Please, rate the app at PlayMarket")
                      .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              if (getApplicationContext() != null) {
                                  String link = "market://details?id=";
                                  try {
                                      // play market available
                                      getPackageManager()
                                              .getPackageInfo("com.deftminds.coronavirusapp", 0);
                                      // not available
                                  } catch (PackageManager.NameNotFoundException e) {
                                      e.printStackTrace();
                                      // should use browser
                                      link = "https://play.google.com/store/apps/details?id=";
                                  }
                                  // starts external action
                                  startActivity(new Intent(Intent.ACTION_VIEW,
                                          Uri.parse(link + getApplication().getPackageName())));
                              }
                          }
                      })
                      .setNegativeButton("CANCEL", null);
              builder.show();
              break;

          case R.id.nav_feedback:
              Intent i = new Intent(MainActivity.this, Feedback.class);
              startActivity(i);
              overridePendingTransition(R.anim.right_in, R.anim.left_out);
              break;

      }
    }

    private void createNotification() {
        //  Toast.makeText(getContext(), "Reminder set!", Toast.LENGTH_SHORT).show();
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    c.set(Calendar.MINUTE, minute);
                    c.set(Calendar.SECOND, 0);
                    startAlarm(c);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, onTimeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Set Reminder:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "YogaReminder";
            String description = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("YogaReminder", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = getApplication().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
        AlarmManager alarmManager = (AlarmManager)getApplication().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}

