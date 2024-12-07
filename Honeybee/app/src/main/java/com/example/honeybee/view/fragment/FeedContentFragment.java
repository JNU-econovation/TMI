package com.example.honeybee.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.model.UserData;
import com.example.honeybee.model.dto.FeedContentDto;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.activity.DetailFeedContentActivity;
import com.example.honeybee.view.adapter.WishAdapter;

import java.util.ArrayList;

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

    private ArrayList<String> user_image;
    private String nickname;
    private Integer age;
    private Integer score;
    private String department;
    private Integer location;
    private String mbti;
    private ArrayList<String> personality;
    private String introduce;
    private String smoke;
    private String drink;
    private Integer height;

    private DetailFeedContentActivity detailFeedContentActivity;

    private FeedContentDto feedContentDto;

    /**
     * String u_id -> 현재 로그인한 사용자 u_id
     */
    private String u_id = "120";
    private String feedUid;

    private WishAdapter wishAdapter;



    // Fragment는 기본생성자를 사용하는것이 거의 강제되기 때문에, 데이터를 전달할 경우
    // 다음과 같이 newInstance()의 인자로 값을 받아서, Bundle에 담아 꺼내 쓰는 방식을 사용해야한다.
    public static FeedContentFragment newInstance(ArrayList<String> user_image,
                                                  String nickname,
                                                  Integer age,
                                                  Integer score,
                                                  String department,
                                                  Integer location,
                                                  String mbti,
                                                  ArrayList<String> personality,
                                                  String introduce,
                                                  String smoke,
                                                  String drink,
                                                  Integer height,
                                                  String feedUid) {

        FeedContentFragment fragment = new FeedContentFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("user_image", user_image);
        args.putString("nickname", nickname);
        args.putInt("age", age);
        args.putInt("score", score);
        args.putString("department", department);
        args.putInt("location", location);
        args.putString("mbti", mbti);
        args.putStringArrayList("personality", personality);
        args.putString("introduce", introduce);
        args.putString("smoke", smoke);
        args.putString("drink", drink);
        args.putInt("height", height);
        args.putString("feedUid", feedUid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFeedContentData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_content, container, false);
        init(view);                         // view.findViewById();
        setFeedContentData();               // feedContentFragment 생성될 때, Bundle로 넘어오는 값을 Fragment에 띄워줌
        addUserIntoWishList(view);
        lookInfoDetail(view);               // 상세정보

        return view;
    }
    private void getFeedContentData() {
        Call<UserData> getBeforeData = NetRetrofit.getInstance().getRetrofitService().userDataFindById(u_id);
        getBeforeData.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                Log.d(TAG,"getFeedContentData nickname = "+feedUid);

                UserData userData = response.body();
                if(userData != null) {
                    ArrayList<String> pick_person = userData.getPick_person();

                    Log.d(TAG,"getFeedContentData compare start");

                    if (!pick_person.contains(feedUid)) {
                        Log.d(TAG,"pick_person not contain nickname");
                        iv_likeButton.setImageResource(R.drawable.ic_wish_unclicked);
                    } else {
                        Log.d(TAG,"pick_person contain nickname");
                        iv_likeButton.setImageResource(R.drawable.ic_wish_clicked);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                Log.d(TAG, "getBeforeData error" + t.getMessage());
            }
        });
    }

    private void setFeedContentData() {
        user_image = getArguments().getStringArrayList("user_image");
        nickname = getArguments().getString("nickname");
        age = getArguments().getInt("age");
        score = getArguments().getInt("score");
        department = getArguments().getString("department");
        location = getArguments().getInt("location");
        mbti = getArguments().getString("mbti");
        personality = getArguments().getStringArrayList("personality");
        introduce = getArguments().getString("introduce");
        smoke = getArguments().getString("smoke");
        drink = getArguments().getString("drink");
        height = getArguments().getInt("height");
        feedUid = getArguments().getString("feedUid");

        feedContentDto = FeedContentDto.builder()
                .user_image(user_image)
                .nickname(nickname)
                .age(age)
                .score(score)
                .department(department)
                .location(location)
                .mbti(mbti)
                .personality(personality)
                .introduce(introduce)
                .smoke(smoke)
                .drink(drink)
                .height(height)
                .build();

        /**
         * 이미지는 이 자리에 Glide 이용해서 링크 달아서 넣어주면 됨
         */

        Log.d(TAG, "이미지 url = " + user_image.get(0));
        Glide.with(getContext()).load(user_image.get(0)).override(1200,1700).into(iv_profile);
//        iv_profile.setImageResource(R.drawable.img_maenji);
        tv_name.setText(feedContentDto.getNickname());
        tv_age.setText(String.valueOf(feedContentDto.getAge()));
        tv_score.setText(String.valueOf(feedContentDto.getScore()));
        tv_personality1.setText(feedContentDto.getPersonality().get(0));
        tv_personality2.setText(feedContentDto.getPersonality().get(1));
        tv_personality3.setText(feedContentDto.getPersonality().get(2));
        tv_personality4.setText(feedContentDto.getPersonality().get(3));
        tv_introduce.setText(feedContentDto.getIntroduce());
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
        intent.putExtra("feedContentDto", feedContentDto);
        intent.putExtra("feedUid", feedUid);
        intent.putExtra("u_id", u_id);
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

    // 찜 목록에 추가하는 메소
    public void addUserIntoWishList(View view) {
        wishAdapter = new WishAdapter();

        iv_likeButton = view.findViewById(R.id.iv_likeButton);
        iv_likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "iv_likeButton click event");

                Call<UserData> getBeforeData = NetRetrofit.getInstance().getRetrofitService().userDataFindById(u_id);
                getBeforeData.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                        UserData userData = response.body();
                        if(userData != null) {
                            ArrayList<String> pick_person = userData.getPick_person();
                            Log.d(TAG, "테스트 ="+pick_person.toString());
                            if (!pick_person.contains(feedUid)) {
                                pick_person.add(feedUid);
                                wishAdapter.addWishUserId(feedUid);
                                iv_likeButton.setImageResource(R.drawable.ic_wish_clicked);

                            } else {
                                pick_person.remove(feedUid);
                                wishAdapter.removeWishUserId(feedUid);
                                iv_likeButton.setImageResource(R.drawable.ic_wish_unclicked);
                                Log.d(TAG, "wishAdapter remove notify");

                            }

                            Call<UserData> patchAfterData = NetRetrofit.getInstance().getRetrofitService().userDataUpdate(u_id, pick_person);
                            patchAfterData.enqueue(new Callback<UserData>() {
                                @Override
                                public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                                    Log.d(TAG, "pick_person" + response.body().getPick_person());
                                }

                                @Override
                                public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                                    Log.d(TAG, "patchAfterData error" + t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                        Log.d(TAG, "getBeforeData error" + t.getMessage());
                    }
                });
            }
        });
    }


}