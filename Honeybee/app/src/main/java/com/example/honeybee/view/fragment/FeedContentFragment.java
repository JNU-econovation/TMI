package com.example.honeybee.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.model.FeedContent;
import com.example.honeybee.model.dto.LikeUserDto;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.activity.DetailFeedContentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedContentFragment extends Fragment implements FeedContract.View {
    private final String TAG = "FeedContentFragment.class";

    private ImageView iv_profile;
    private TextView tv_name;
    private TextView tv_age;
    private ImageView iv_likeButton;
    private TextView tv_score;
    private ImageView iv_score;
    private TextView tv_personality1;
    private TextView tv_personality2;
    private TextView tv_personality3;
    private TextView tv_personality4;
    private TextView tv_introduce;

    private String profileUrl;
    private String name;
    private int age;
    private int score;
    private String[] personalities;
    private String introduce;

    private DetailFeedContentActivity detailFeedContentActivity;

    public FeedContentFragment() {

    }

    public static FeedContentFragment newInstance() {
        FeedContentFragment fragment = new FeedContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_content, container, false);
        init(view);                         // view.findViewById();
        getFeedContentFromServer();
        lookInfoDetail(view);               // 상세정보


        return view;
    }

    public void addToLikeList() {
        iv_likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getFeedContentFromServer() {

        String id = "61e3f0c2aa45187a63ab63bf";
        Call<FeedContent> data = NetRetrofit.getInstance().getRetrofitService().findById(id);
        data.enqueue(new Callback<FeedContent>() {
            @Override
            public void onResponse(Call<FeedContent> call, Response<FeedContent> response) {
                profileUrl = response.body().getProfile();
                name = response.body().getName();
                age = response.body().getAge();
                score = response.body().getScore();
                personalities = response.body().getPersonalities();
                introduce = response.body().getIntroduce();

                for (String personality : personalities) {
                    Log.d(TAG, "personalities = " + personality);
                }

//                Glide.with(getContext()).load(profileUrl).into(iv_profile);
                iv_profile.setImageResource(R.drawable.img_maenji);
                tv_name.setText(name);
                tv_age.setText(String.valueOf(age));
                tv_score.setText(String.valueOf(score));
                tv_personality1.setText(personalities[0]);
                tv_personality2.setText(personalities[1]);
                tv_personality3.setText(personalities[2]);
                tv_personality4.setText(personalities[3]);
                tv_introduce.setText(introduce);
            }

            @Override
            public void onFailure(Call<FeedContent> call, Throwable t) {

            }
        });
    }

    public void init(View view) {
        iv_profile = view.findViewById(R.id.iv_profile);
        tv_name = view.findViewById(R.id.tv_name);
        tv_age = view.findViewById(R.id.tv_age);
        iv_likeButton = view.findViewById(R.id.iv_likeButton);
        tv_score = view.findViewById(R.id.tv_score);
        tv_personality1 = view.findViewById(R.id.tv_personality1);
        tv_personality2 = view.findViewById(R.id.tv_personality2);
        tv_personality3 = view.findViewById(R.id.tv_personality3);
        tv_personality4 = view.findViewById(R.id.tv_personality4);
        tv_introduce = view.findViewById(R.id.tv_introduce);
    }

    @Override
    public void addFragment(Fragment fragment) { }

    @Override
    public void moveActivity(Activity activity) {
        Intent intent = new Intent(getContext(), activity.getClass());

        intent.putExtra("profile", profileUrl);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        intent.putExtra("score", score);
        intent.putExtra("personality1", personalities[0]);
        intent.putExtra("personality2", personalities[1]);
        intent.putExtra("personality3", personalities[2]);
        intent.putExtra("personality4", personalities[3]);
        intent.putExtra("personality5", personalities[4]);
        intent.putExtra("personality6", personalities[5]);
        intent.putExtra("personality7", personalities[6]);
        intent.putExtra("introduce", introduce);

        startActivity(intent);
    }

    public void lookInfoDetail(View view) {
        iv_profile = view.findViewById(R.id.iv_profile);
        FeedContract.Presenter presenter = new FeedContentPresenterImpl(this);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailFeedContentActivity = new DetailFeedContentActivity();
                presenter.setActivity(detailFeedContentActivity);
            }
        });
    }

    public void addUserIntoLikeList(View view) {
        iv_likeButton = view.findViewById(R.id.iv_likeButton);
        iv_likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "61e3f0c2aa45187a63ab63bf";
                Call<LikeUserDto> likeUserDtoCall = NetRetrofit.retrofitService.getLikeUser(id);
                likeUserDtoCall.enqueue(new Callback<LikeUserDto>() {
                    @Override
                    public void onResponse(Call<LikeUserDto> call, Response<LikeUserDto> response) {

                    }

                    @Override
                    public void onFailure(Call<LikeUserDto> call, Throwable t) {

                    }
                });
            }
        });
    }


}