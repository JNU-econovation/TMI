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

    private String u_id = "김현지";

    private ArrayList<String> wishUsers = new ArrayList<>();
    private ArrayList<String> wishImages = new ArrayList<>();


    public WishAdapter(ArrayList<String> wishUsers, ArrayList<String> wishImages) {
        this.wishUsers = wishUsers;
        this.wishImages = wishImages;
    }

    public WishAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() 호출 ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item, parent, false);

        return new ViewHolder(view, wishUsers, wishImages, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() wishImages = " + wishImages);
        Log.d(TAG, "onBindViewHolder() wishUsers = " + wishUsers);

    }

    @Override
    public int getItemCount() {
        return wishImages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addWishUserImage(String user_image) {
        wishImages.add(user_image);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeWishUserImage(String user_image) {
        wishImages.remove(user_image);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addWishUserId(String u_Id) {
        wishUsers.add(u_Id);
        notifyDataSetChanged();
    }

    public void removeWishUserId(String u_id) {
        wishUsers.remove(u_id);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = "ViewHolder.class";
        private ImageView iv_profile;
        private ImageView iv_wish;
        private FeedContentDto feedContentDto;
        private Intent detailFeedIntent;

        public ViewHolder(@NonNull View itemView,ArrayList<String> wishUsers, ArrayList<String> wishImages, WishAdapter wishAdapter) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_wish = itemView.findViewById(R.id.iv_wish);

            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "iv_profile clicked");
                    Call<UserData> userDataFindById = NetRetrofit.getInstance().getRetrofitService().userDataFindById(wishAdapter.u_id);
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
                            itemView.getContext().startActivity(detailFeedIntent);
                        }

                        @Override
                        public void onFailure(Call<UserData> call, Throwable t) {

                        }
                    });

                }
            });

            iv_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "iv_wish clicked userImage=" +  wishImages.get(getAdapterPosition()));
                    Log.d(TAG, "iv_wish clicked wishUserId=" + wishUsers.get(getAdapterPosition()));

                    wishAdapter.removeWishUserId(wishUsers.get(getAdapterPosition()));
                    wishAdapter.removeWishUserImage(wishImages.get(getAdapterPosition()));
                    Log.d(TAG, "iv_wish clicked userImage after removed=" +  wishImages);

                    Call<UserData> userDataUpdate = NetRetrofit.getInstance().getRetrofitService().userDataUpdate(wishAdapter.u_id, wishAdapter.wishUsers);
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

        }
    }

}
