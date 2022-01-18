package com.example.honeybee.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.model.dto.FeedContentDto;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.activity.DetailFeedContentActivity;

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

    private String[] user_image;
    private String nickname;
    private Integer age;
    private String[] personality;
    private String introduce;

    private DetailFeedContentActivity detailFeedContentActivity;


    // Fragment는 기본생성자를 사용하는것이 거의 강제되기 때문에, 데이터를 전달할 경우
    // 다음과 같이 newInstance()의 인자로 값을 받아서, Bundle에 담아 꺼내 쓰는 방식을 사용해야한다.
    public static FeedContentFragment newInstance(String[] user_image, String nickname, Integer age, String[] personality, String introduce) {

        FeedContentFragment fragment = new FeedContentFragment();
        Bundle args = new Bundle();
        args.putStringArray("user_image", user_image);
        args.putString("nickname", nickname);
        args.putInt("age", age);
        args.putStringArray("personality", personality);
        args.putString("introduce", introduce);
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
        addUserIntoLikeList(view);

        return view;
    }

    private void setFeedContentData() {
        user_image = getArguments().getStringArray("user_image");
        nickname = getArguments().getString("nickname");
        age = getArguments().getInt("age");
        personality = getArguments().getStringArray("personality");
        introduce = getArguments().getString("introduce");

        FeedContentDto feedContentDto = FeedContentDto.builder()
                .user_image(user_image)
                .nickname(nickname)
                .age(age)
                .personality(personality)
                .introduce(introduce)
                .build();

        /**
         * 이미지는 이 자리에 Glide 이용해서 링크 달아서 넣어주면 됨
         */
        iv_profile.setImageResource(R.drawable.img_maenji);
        tv_name.setText(feedContentDto.getNickname());
        tv_age.setText(String.valueOf(feedContentDto.getAge()));
        tv_personality1.setText(feedContentDto.getPersonality()[0]);
        tv_personality2.setText(feedContentDto.getPersonality()[1]);
        tv_personality3.setText(feedContentDto.getPersonality()[2]);
        tv_personality4.setText(feedContentDto.getPersonality()[3]);
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

        intent.putExtra("user_image", user_image);
        intent.putExtra("nickname", nickname);
        intent.putExtra("age", age);
        intent.putExtra("personality1", personality[0]);
        intent.putExtra("personality2", personality[1]);
        intent.putExtra("personality3", personality[2]);
        intent.putExtra("personality4", personality[3]);
        intent.putExtra("personality5", personality[4]);
        intent.putExtra("personality6", personality[5]);
        intent.putExtra("personality7", personality[6]);
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
                Log.d(TAG, "nickname =" + nickname);
            }
        });
    }


}