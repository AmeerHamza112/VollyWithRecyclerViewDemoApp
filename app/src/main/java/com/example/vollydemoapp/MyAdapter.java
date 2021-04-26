package com.example.vollydemoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Data> list;


    public MyAdapter(Context ct, List<Data> lst) {
        this.context = ct;
        this.list = lst;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            textView = (TextView) v.findViewById(R.id.text_view);
            imageView = (ImageView) v.findViewById(R.id.image_view);
        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_main, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        Data mainData=list.get(position);

        //Set Image on image view
        Glide.with(context).load(mainData.getImage())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView);

        //Set name in text view

        holder.textView.setText(mainData.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
