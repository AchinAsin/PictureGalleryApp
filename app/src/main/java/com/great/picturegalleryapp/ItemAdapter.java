package com.great.picturegalleryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Photo> photo;
    private Context context;

    public ItemAdapter(ArrayList<Photo> photo, Context context) {
        this.photo = photo;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_files, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*Photos positions = items.get(position);
        holder.textView1.setText(positions.getPage());
        holder.textView2.setText(positions.getPages());
        holder.textView3.setText(positions.getPerpage());
        holder.textView4.setText(positions.getTotal());*/

        Photo positions=photo.get(position);
        /*holder.textView1.setText(positions.getId());
        holder.textView2.setText(positions.getTitle());
        holder.textView3.setText(positions.getIsfamily());
        holder.textView4.setText(positions.getUrlS());*/
        holder.textView.setText(positions.getTitle());
        Glide.with(context).load(positions.getUrlS()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*private TextView textView1, textView2, textView3, textView4;*/
        private ImageView imageView;
        private TextView textView;


        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            /*textView2 = (TextView) view.findViewById(R.id.textView2);
            textView3 = (TextView) view.findViewById(R.id.textView3);
            textView4 = (TextView) view.findViewById(R.id.textView4);*/
            imageView = (ImageView) view.findViewById(R.id.imageView);

            //on item click
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Photo clickedDataItem = photo.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("title", photo.get(pos).getTitle());
                        intent.putExtra("url_s", photo.get(pos).getUrlS());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

            });
        }
    }
}

