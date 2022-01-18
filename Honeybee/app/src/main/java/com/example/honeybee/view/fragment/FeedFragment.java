package com.example.honeybee.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.model.FeedContent;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.activity.DetailFeedContentActivity;
import com.example.honeybee.view.adapter.MainPageAdapter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedFragment extends Fragment implements FeedContract.View{
    private static final String TAG = "FeedFragment.class";
    private ViewPager2 pager;
    private MainPageAdapter pagerAdapter;
    private FeedContract.Presenter presenter;

    private DetailFeedContentActivity detailFeedContentActivity;

    private List<FeedContent> allFeedContent;

    private String profileUrl;
    private String name;
    private int age;
    private int score;
    private String[] personalities;
    private String introduce;
    private String[] userId;

    private FeedFragment() {
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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        Log.d(TAG, "onCreateView 호출");

        acitvateFeedPager(view);

        return view;
    }

    public void acitvateFeedPager(View view) {
        presenter = new FeedContentPresenterImpl(this);

        Call<List<FeedContent>> listCall = NetRetrofit.retrofitService.findAll();
        listCall.enqueue(new Callback<List<FeedContent>>() {
            @Override
            public void onResponse(Call<List<FeedContent>> call, Response<List<FeedContent>> response) {
                allFeedContent = response.body();
                for (FeedContent feedContent : allFeedContent) {
                    Log.d(TAG, "allFeedContent = " + allFeedContent);
                }

                for (FeedContent feedContent : allFeedContent) {
                    profileUrl = feedContent.getProfile();
                    name = feedContent.getName();
                    age = feedContent.getAge();
                    score = feedContent.getScore();
                    personalities = feedContent.getPersonalities();
                    introduce = feedContent.getIntroduce();
                    userId = feedContent.getUserId();

                    presenter.setFragment(FeedContentFragment.newInstance(
                            profileUrl, name, age, score, personalities, introduce, userId));
                }
            }

            @Override
            public void onFailure(Call<List<FeedContent>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

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

        pager.post(new Runnable() {
            @Override
            public void run() {
                pagerAdapter.notifyDataSetChanged();
            }
        });
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