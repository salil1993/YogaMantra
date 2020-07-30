package com.deftminds.coronavirusapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.CountdownActivity;
import com.deftminds.coronavirusapp.activity.ReadyToGoActivity;
import com.deftminds.coronavirusapp.activity.Yogasana;
import com.deftminds.coronavirusapp.model.Yoga;


import java.util.ArrayList;
import java.util.List;

import ticker.views.com.ticker.widgets.circular.timer.callbacks.CircularViewCallback;
import ticker.views.com.ticker.widgets.circular.timer.view.CircularView;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.YogaViewHolder> {

    private static final String TAG = "YogaAdapter";
    //this context we will use to inflate the layout
    private Context mCtx;
    String day;
    //we are storing all the products in a list
    private List<Yoga> yogaArrayList = new ArrayList<>();

    PopupWindow popupWindow;
    private ImageView pause, resume,iv_back;
    private CircularView circularViewWithTimer;
    private boolean isPause = false;
    private String time, description, pose, image;
    private TextView tv_skip, tv_pose_details,title;
    int i =0;

    public YogaAdapter(Context mCtx, List<Yoga> yogaArrayList) {
        this.mCtx = mCtx;
        this.yogaArrayList = yogaArrayList;

    }



    @NonNull
    @Override
    public YogaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_yoga, null);
        return new YogaAdapter.YogaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaViewHolder holder, int position) {
        //getting the product of the specified position
        Yoga yoga = yogaArrayList.get(position);
        //binding the data with the viewholder views
        holder.tv_pose.setText(yoga.getPose());
        holder.tv_time.setText(yoga.getTime()+" s");

        Glide.with(mCtx )
                .load(yoga.getUrl())
                .fitCenter()
                .into(holder.iv_img);



    }

    @Override
    public int getItemCount() {
        return yogaArrayList.size();
    }

    public class YogaViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pose, tv_time;
        ImageView iv_img;
        LinearLayout linearLayout1;
        public YogaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pose =(TextView)itemView.findViewById(R.id.tv_pose);
            tv_time =(TextView)itemView.findViewById(R.id.tv_time);
            iv_img = (ImageView)itemView.findViewById(R.id.iv_img);
            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.linearLayout1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //instantiate the popup.xml layout file
                    LayoutInflater layoutInflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.activity_countdown,null);

                    circularViewWithTimer = customView.findViewById(R.id.circular_view_with_custom_text);
                    CircularView.OptionsBuilder builderWithTimer = new
                            CircularView.OptionsBuilder()
                            .shouldDisplayText(true)
                            .setCounterInSeconds(10)
                            .setCircularViewCallback(new CircularViewCallback() {
                                @Override
                                public void onTimerFinish() {
                                    try {
                                        if (i==0) {
                                            popupWindow.dismiss();
                                            Intent i = new Intent(mCtx, ReadyToGoActivity.class);
                                            i.putExtra("time", yogaArrayList.get(getAdapterPosition()).getTime());
                                            i.putExtra("description", yogaArrayList.get(getAdapterPosition()).getPose_desc());
                                            i.putExtra("pose", yogaArrayList.get(getAdapterPosition()).getPose());
                                            i.putExtra("image", yogaArrayList.get(getAdapterPosition()).getUrl());
                                            mCtx.startActivity(i);
                                            ((Yogasana)mCtx).finish();

                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    //  Toast.makeText(CountdownActivity.this, "CircularCallback: Timer Finished ", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onTimerCancelled() {
                                    Toast.makeText(mCtx, "CircularCallback: Timer Cancelled ", Toast.LENGTH_SHORT).show();
                                }
                            });
                    circularViewWithTimer.setOptions(builderWithTimer);
                    tv_pose=customView.findViewById(R.id.tv_pose);
                    tv_pose_details=customView.findViewById(R.id.tv_pose_details);
                    pause = customView.findViewById(R.id.pause);
                    resume = customView.findViewById(R.id.resume);
                    tv_skip = customView.findViewById(R.id.tv_skip);
                    tv_pose_details.setText(yogaArrayList.get(getAdapterPosition()).getPose_desc());
                    tv_pose.setText(yogaArrayList.get(getAdapterPosition()).getPose());

                    resume.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    circularViewWithTimer.startTimer();

                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    //display the popup window
                    popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                    //close the popup window on button click
                   tv_skip.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           i=1;
                           popupWindow.dismiss();
                           Intent intent = new Intent(mCtx, ReadyToGoActivity.class);
                           intent.putExtra("time", yogaArrayList.get(getAdapterPosition()).getTime());
                           intent.putExtra("description", yogaArrayList.get(getAdapterPosition()).getPose_desc());
                           intent.putExtra("pose", yogaArrayList.get(getAdapterPosition()).getPose());
                           intent.putExtra("image", yogaArrayList.get(getAdapterPosition()).getUrl());
                           mCtx.startActivity(intent);
                           ((Yogasana)mCtx).finish();

                       }
                   });

                  pause.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (circularViewWithTimer.pauseTimer()) {
                              isPause = true;
                              resume.setVisibility(View.VISIBLE);
                              pause.setVisibility(View.INVISIBLE);

                          } else {

                              Toast.makeText(mCtx, "Timer finished before pausing", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
                 resume.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if (isPause) {
                             resume.setVisibility(View.INVISIBLE);
                             pause.setVisibility(View.VISIBLE);
                             circularViewWithTimer.resumeTimer();
                             isPause = false;

                         } else {
                             Toast.makeText(mCtx, "Timer Not Paused before", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });







                   /* Intent i = new Intent(mCtx, CountdownActivity.class);
                    i.putExtra("time",yogaArrayList.get(getPosition()).getTime());
                    i.putExtra("description",yogaArrayList.get(getPosition()).getPose_desc());
                    i.putExtra("pose",yogaArrayList.get(getAdapterPosition()).getPose());
                    i.putExtra("image",yogaArrayList.get(getPosition()).getUrl());
                    mCtx.startActivity(i);*/
                }
            });
        }
    }
}
