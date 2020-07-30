package com.deftminds.coronavirusapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.PoseActivity;
import com.deftminds.coronavirusapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final String TAG = "HomeAdapter";
    //this context we will use to inflate the layout
    private Context mCtx;
    //we are storing all the products in a list
    private List<Product> productList = new ArrayList<>();

    //getting the context and product list with constructor
    public HomeAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_home, null);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = productList.get(position);
        //binding the data with the viewholder views
        holder.tv_Title.setText(product.getTitle());
        if (product.getSubtitle().contentEquals("Beginners")){
            holder.tv_subtitle.setText(product.getSubtitle());
            holder.tv_subtitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_beginner, 0, 0, 0);
        }else if (product.getSubtitle().contentEquals("Intermediate")){
            holder.tv_subtitle.setText(product.getSubtitle());
            holder.tv_subtitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_intermediate, 0, 0, 0);
        }else{
            holder.tv_subtitle.setText(product.getSubtitle());
            holder.tv_subtitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_advanced, 0, 0, 0);
        }
        holder.tv_subtitle.setText(product.getSubtitle());
        holder.tv_time.setText(String.valueOf(product.getTime()));
        holder.tv_calories.setText(String.valueOf(product.getCalories()));
        Log.e("Firebase", "image url = " + product.getImages());
   /*     holder.iv_img.setImageDrawable(Drawable.createFromPath(product.getImages()));*/
/*        Glide.with(mCtx)
                .load(String.valueOf(product.getImages()))
                .centerCrop()
                .into(holder.iv_img);*/
       /* ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mCtx));
        //set the profile image
        final ImageLoader imageLoader = ImageLoader.getInstance();*/

      //  FirebaseStorage storage = FirebaseStorage.getInstance();
     //  StorageReference  storageRef = storage.getReferenceFromUrl(product.getImages());


        Glide.with(mCtx )
                .load(product.getImages())
                .into(holder.iv_img);
    }

    @Override
    public int getItemCount()
    {
        return productList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title, tv_subtitle, tv_time, tv_calories;
        ImageView iv_img;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title =(TextView)itemView.findViewById(R.id.tv_title);
            tv_subtitle =(TextView)itemView.findViewById(R.id.tv_subtitle);
            tv_time =(TextView)itemView.findViewById(R.id.tv_time);
            tv_calories =(TextView)itemView.findViewById(R.id.tv_calories);
            iv_img = (ImageView)itemView.findViewById(R.id.iv_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(mCtx, PoseActivity.class);
                    intent.putExtra("title",productList.get(getAdapterPosition()).getTitle());
                    mCtx.startActivity(intent);
                }
            });
        }
    }
}
