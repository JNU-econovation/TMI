package com.example.honeybee.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.model.UserData;
import com.example.honeybee.model.dto.FeedContentDto;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.activity.DetailFeedContentActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {
    private final String TAG = "WishAdapter.class";

    private String u_id = "120";

    private ArrayList<String> wishUsers = new ArrayList<>();

    public WishAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() 호출 ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item, parent, false);

        return new ViewHolder(view, wishUsers,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() wishUsers = " + wishUsers);
        holder.iv_wish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.d(TAG, "iv_wish clicked wishUserId=" + wishUsers.get(holder.getAdapterPosition()));

               removeWishUserId(wishUsers.get(holder.getAdapterPosition()));

               Call<UserData> userDataUpdate = NetRetrofit.getInstance().getRetrofitService().userDataUpdate(u_id, wishUsers);
               userDataUpdate.enqueue(new Callback<UserData>() {
                   @Override
                   public void onResponse(Call<UserData> call, Response<UserData> response) {
                       Log.d(TAG, "userDataUpdate response = " + response.body());
                   }

                   @Override
                   public void onFailure(Call<UserData> call, Throwable t) {

                   }
               });
           }
       });

        Call<UserData> userDataFindById = NetRetrofit.retrofitService.userDataFindById(wishUsers.get(position));
        userDataFindById.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData userData = response.body();
                String imageUrl = userData.getUser_image().get(0);
                Glide.with(holder.itemView).load(imageUrl).into(holder.iv_profile);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d(TAG, "사진 업데이트 = " + t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return wishUsers.size();
    }

    public void addWishUserId(String u_Id) {
        wishUsers.add(u_Id);
        notifyDataSetChanged();
    }

    public void removeWishUserId(String u_id) {
        wishUsers.remove(u_id);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = "ViewHolder.class";
        private ImageView iv_profile;
        private ImageView iv_wish;
        private FeedContentDto feedContentDto;
        private Intent detailFeedIntent;

        public ViewHolder(@NonNull View itemView,ArrayList<String> wishUsers, WishAdapter wishAdapter) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_wish = itemView.findViewById(R.id.iv_wish);

            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "iv_profile clicked");
                    Call<UserData> userDataFindById = NetRetrofit.getInstance().getRetrofitService().userDataFindById(wishUsers.get(getAdapterPosition()));
                    userDataFindById.enqueue(new Callback<UserData>() {
                        @Override
                        public void onResponse(Call<UserData> call, Response<UserData> response) {
                            UserData userData = response.body();

                            feedContentDto = FeedContentDto.builder()
                                    .user_image(userData.getUser_image())
                                    .nickname(userData.getNickname())
                                    .age(userData.getAge())
                                    .department(userData.getDepartment())
                                    .location(userData.getLocation())
                                    .mbti(userData.getMbti())
                                    .personality(userData.getPersonality())
                                    .introduce(userData.getIntroduce())
                                    .smoke(userData.getSmoking())
                                    .drink(userData.getDringking())
                                    .height(userData.getHeight())
                                    .build();

                            detailFeedIntent = new Intent(itemView.getContext(), DetailFeedContentActivity.class);
                            detailFeedIntent.putExtra("feedContentDto", feedContentDto);
                            detailFeedIntent.putExtra("feedUid", wishUsers.get(getAdapterPosition()));
                            itemView.getContext().startActivity(detailFeedIntent);
                        }

                        @Override
                        public void onFailure(Call<UserData> call, Throwable t) {

                        }
                    });

                }
            });



        }
    }

}
