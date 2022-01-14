package com.example.honeybee.view.fragment;

import android.app.Activity;
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
import com.example.honeybee.view.adapter.MainPageAdapter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedFragment extends Fragment implements FeedContract.View{
    private static final String TAG = "FeedFragment.class";
    private ViewPager2 pager;
    private MainPageAdapter pagerAdapter;
    private FeedContract.Presenter presenter;

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
        acitvateFeedPager(view);

//        Call<FeedContent> userFeedInfo = NetRetrofit.getInstance().getRetrofitService().getDatas("userId");
//        userFeedInfo.enqueue(new Callback<FeedContent>() {
//            @Override
//            public void onResponse(Call<FeedContent> call, Response<FeedContent> response) {
//                Log.d(TAG, "성공");
//
//            }
//
//            @Override
//            public void onFailure(Call<FeedContent> call, Throwable t) {
//                Log.d(TAG, "실패");
//            }
//        });


        return view;
    }

    public void acitvateFeedPager(View view) {
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

        presenter = new FeedContentPresenterImpl(this);
        presenter.setFragment(FeedContentFragment.newInstance());

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

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels == 0) {
                    Log.d(TAG, "onPageScrolled() 호출");

                    //아래로 스크롤 할때마다 동적으로 아래 페이지가 생성
                    presenter.setFragment(FeedContentFragment.newInstance());
                    pager.setCurrentItem(position);

                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public void addFragment(Fragment fragment) {
        Log.d(TAG, "addFragment() 호출" + fragment.toString());
        pagerAdapter.addItem(fragment);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void moveActivity(Activity activity) {

    }

}