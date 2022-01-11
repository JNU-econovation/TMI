package com.example.honeybee.presenter;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.honeybee.contract.FeedContract;

public class FeedPresenterImpl implements FeedContract.Presenter {
    private static final String TAG = "FeedPresenterImpl.class";
    private FeedContract.View view;

    public FeedPresenterImpl(FeedContract.View view) {
        this.view = view;
    }

    @Override
    public void setFragment(Fragment fragment) {
        Log.d(TAG, "setFragment() 호출" + fragment.toString());
        view.addFragment(fragment);
    }
}
