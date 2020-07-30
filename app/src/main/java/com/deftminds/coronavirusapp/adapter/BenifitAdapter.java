package com.deftminds.coronavirusapp.adapter;

import android.content.Context;
import android.content.Intent;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.model.Benefit;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BenifitAdapter extends RecyclerView.Adapter<BenifitAdapter.BenifitViewHolder> {
    private static final String TAG = "BenifitAdapter";
    //this context we will use to inflate the layout
    private Context mCtx;
    //we are storing all the products in a list
    private List<Benefit> benefitList = new ArrayList<>();

    //getting the context and product list with constructor
    public BenifitAdapter(Context mCtx, List<Benefit> benefitList) {
        this.mCtx = mCtx;
        this.benefitList = benefitList;
    }
    @NonNull
    @Override
    public BenifitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_benifit, null);
        return new BenifitAdapter.BenifitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BenifitViewHolder holder, int position) {
        //getting the product of the specified position
        Benefit benefit = benefitList.get(position);
        //binding the data with the viewholder views
        holder.tv_Title.setText(benefit.getTitle());
        Glide.with(mCtx )
                .load(benefit.getImages())
                .into(holder.iv_img);

        holder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap =getBitmapFromView(holder.iv_img);
                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, benefit.getTitle() + "\n" + "https://play.google.com/store/apps/details?id=com.deftminds.coronavirusapp");
                    sendIntent.setType("text/plain");
                    Intent.createChooser(sendIntent, "Share via");
                    mCtx.startActivity(sendIntent);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
    @Override
    public int getItemCount() {
        return benefitList.size();
    }

    public class BenifitViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title, tv_share;
        ImageView iv_img;
        public BenifitViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title =(TextView)itemView.findViewById(R.id.tv_title);
            iv_img = (ImageView)itemView.findViewById(R.id.iv_img);
            tv_share =(TextView)itemView.findViewById(R.id.tv_share);


        }
    }
}
