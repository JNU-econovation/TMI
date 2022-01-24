package com.example.honeybee.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.widget.ViewPager2;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.honeybee.BuildConfig;
import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.model.UserData;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.activity.DetailFeedContentActivity;
import com.example.honeybee.view.adapter.MainPageAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedFragment extends Fragment implements FeedContract.View{
    private static final String TAG = "FeedFragment.class";
    private ViewPager2 pager;
    private MainPageAdapter pagerAdapter;
    private FeedContract.Presenter presenter;

    private ArrayList<String> user_image;
    private String nickname;
    private Integer age;
    private ArrayList<String> personality;
    private String introduce;

    public FeedFragment() {
    }

    public static FeedFragment newInstance() {
        Log.d(TAG, "newInstance() 호출");
        Bundle bundle = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFeedData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        Log.d(TAG, "onCreateView 호출");

        activateFeedPager(view);

        return view;
    }

    public void activateFeedPager(View view) {
        presenter = new FeedContentPresenterImpl(this);

        pager = view.findViewById(R.id.viewPager);
        pagerAdapter = new MainPageAdapter(getChildFragmentManager(), new Lifecycle() {
            @Override
            public void addObserver(@NonNull LifecycleObserver observer) { }

            @Override
            public void removeObserver(@NonNull LifecycleObserver observer) { }

            @NonNull
            @Override
            public State getCurrentState() { return null; }
        });

        pager.setAdapter(pagerAdapter);
        pager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        pager.setOffscreenPageLimit(3);

        // viewPager2 페이지간 간격 조절 => 다음 페이지, 이전 페이지 미리보기
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);

        pager.setPadding(0, margin, 0, margin);
        pager.setClipToOutline(true);

        float pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.vertical_margin);
        float pagerHeight = getResources().getDimensionPixelOffset(R.dimen.vertical_margin);
        float screenHeight = getResources().getDisplayMetrics().widthPixels;
        float offsetPx = screenHeight - pageMarginPx - pagerHeight;

        pager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationY(position * -offsetPx);
            }
        });

        // 페이지 선택, 스크롤에 해당하는 이벤트
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels == 0) {
                    Log.d(TAG, "onPageScrolled() 호출");

                    pager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected 호출 = " + position);
            }
        });

        try {
            Thread.sleep(700);                 // 느린렌더링 문제떄문에 처리해주어야함
            pager.post(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    pagerAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception ignored) {

        }
        pager.setSaveEnabled(false);
    }

    public void getFeedData() {
        Log.d(TAG, "getFeedData() called");
        if (getArguments() != null) {
            Call<List<UserData>> listCall = NetRetrofit.retrofitService.userDatafindAll();
            listCall.enqueue(new Callback<List<UserData>>() {
                @SneakyThrows
                @Override
                public void onResponse(@NonNull Call<List<UserData>> call, @NonNull Response<List<UserData>> response) {
                    List<UserData> userDatas = response.body();
                    assert userDatas != null;
                    for (UserData data : userDatas) {
                        Log.d(TAG, "tmiData = " + data);
                    }

                    for (UserData userData : userDatas) {
                        user_image = userData.getUser_image();
                        nickname = userData.getNickname();
                        age = userData.getAge();
                        personality = userData.getPersonality();
                        introduce = userData.getIntroduce();

                        presenter.setFragment(FeedContentFragment.newInstance(
                                user_image, nickname, age, personality, introduce));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<UserData>> call, @NonNull Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }
    }

    @Override
    public void addFragment(Fragment fragment) {
        Log.d(TAG, "addFragment() 호출" + fragment.toString());
        pagerAdapter.addItem(fragment);
    }

    @Override
    public void moveActivity(Activity activity) {
    }

}