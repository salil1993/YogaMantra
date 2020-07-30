package com.deftminds.coronavirusapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.StepsActivity;
import com.deftminds.coronavirusapp.model.Pose;

import java.util.ArrayList;
import java.util.List;

public class PoseAdapter extends RecyclerView.Adapter<PoseAdapter.PoseViewHolder> {

    private static final String TAG = "PoseAdapter";
    //this context we will use to inflate the layout
    private Context mCtx;
    String header;
    //we are storing all the products in a list
    private List<Pose> poseArrayList = new ArrayList<>();



    public PoseAdapter(Context mCtx, List<Pose> poseArrayList, String header) {
        this.mCtx = mCtx;
        this.poseArrayList = poseArrayList;
        this.header=header;
    }

    @NonNull
    @Override
    public PoseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_pose, null);
        return new PoseAdapter.PoseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoseViewHolder holder, int position) {
        //getting the product of the specified position
        Pose pose = poseArrayList.get(position);
        //binding the data with the viewholder views
        holder.tv_Title.setText(pose.getHeading());

        Glide.with(mCtx )
                .load(pose.getPhoto())
                .fitCenter()
                .into(holder.iv_img);
    }

    @Override
    public int getItemCount() {
        return poseArrayList.size();
    }

    public class PoseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title;
        ImageView iv_img;
        public PoseViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title =(TextView)itemView.findViewById(R.id.tv_title);
            iv_img = (ImageView)itemView.findViewById(R.id.iv_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, StepsActivity.class);
                    i.putExtra("pose", poseArrayList.get(getAdapterPosition()).getHeading());
                    i.putExtra("header", header);
                    i.putExtra("image",poseArrayList.get(getAdapterPosition()).getPhoto());
                    mCtx.startActivity(i);
                }
            });
        }
    }
}
