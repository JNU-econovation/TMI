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
    private String[] userId;

    private DetailFeedContentActivity detailFeedContentActivity;


    // Fragment는 기본생성자를 사용하는것이 거의 강제되기 때문에, 데이터를 전달할 경우
    // 다음과 같이 newInstance()의 인자로 값을 받아서, Bundle에 담아 꺼내 쓰는 방식을 사용해야한다.
    public static FeedContentFragment newInstance(String profileUrl, String name, int age, int score, String[] personalities, String introduce, String[] userId) {

        FeedContentFragment fragment = new FeedContentFragment();
        Bundle args = new Bundle();
        args.putString("profileUrl", profileUrl);
        args.putString("name", name);
        args.putInt("age", age);
        args.putInt("score", score);
        args.putStringArray("personalities", personalities);
        args.putString("introduce", introduce);
        args.putStringArray("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_content, container, false);
        init(view);                         // view.findViewById();
//        getFeedContentFromServer();         // 서버로부터 피드 받아와서 뿌려줌
        lookInfoDetail(view);               // 상세정보
        setFeedContentData();               // feedContentFragment 생성될 때, Bundle로 넘어오는 값을 Fragment에 띄워줌

        return view;
    }

    private void setFeedContentData() {

        profileUrl = getArguments().getString("profileUrl");
        name = getArguments().getString("name");
        age = getArguments().getInt("age");
        score = getArguments().getInt("score");
        personalities = getArguments().getStringArray("personalities");
        introduce = getArguments().getString("introduce");
        userId = getArguments().getStringArray("userId");

        FeedContent feedContent = FeedContent.builder()
                .profile(profileUrl)
                .name(name)
                .age(age)
                .score(score)
                .personalities(personalities)
                .introduce(introduce)
                .build();

        iv_profile.setImageResource(R.drawable.img_maenji);
        tv_name.setText(feedContent.getName());
        tv_age.setText(String.valueOf(feedContent.getAge()));
        tv_score.setText(String.valueOf(feedContent.getAge()));
        tv_personality1.setText(feedContent.getPersonalities()[0]);
        tv_personality2.setText(feedContent.getPersonalities()[1]);
        tv_personality3.setText(feedContent.getPersonalities()[2]);
        tv_personality4.setText(feedContent.getPersonalities()[3]);
        tv_introduce.setText(feedContent.getIntroduce());
    }

    public void addToLikeList() {
        iv_likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


    // 찜 목록에 추가하는 메소
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