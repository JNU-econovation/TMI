package com.example.honeybee.view.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.honeybee.R;

import java.util.ArrayList;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {
    private final String TAG = "WishAdapter.class";
    private ArrayList<String> wishLists = new ArrayList<>();


    public WishAdapter() {
    }

    public WishAdapter(ArrayList<String> wishLists) {
        this.wishLists = wishLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() 호출 ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item, parent, false);
        return new ViewHolder(view, wishLists);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() wishLists = " + wishLists);
        String s = wishLists.get(position);
    }

    @Override
    public int getItemCount() {
        return wishLists.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(String user_image) {
        wishLists.add(user_image);
        notifyDataSetChanged();
    }

    public void removeItem(String item) {
        wishLists.remove(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = "ViewHolder.class";
        private ImageView iv_profile;
        private ImageView iv_wish;

        private ArrayList<String> wishLists = new ArrayList<>();

        public ViewHolder(@NonNull View itemView, ArrayList<String> wishLists) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_wish = itemView.findViewById(R.id.iv_wish);

            this.wishLists = wishLists;

            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "iv_profile clicked");
                }
            });

            iv_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "iv_wish clicked =" +  wishLists.get(getAdapterPosition()));

                }
            });

        }
    }

}
