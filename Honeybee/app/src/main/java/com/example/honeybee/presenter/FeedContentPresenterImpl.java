package com.example.honeybee.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.honeybee.contract.FeedContract;

public class FeedContentPresenterImpl implements FeedContract.Presenter {
    private static final String TAG = "FeedPresenterImpl.class";
    private FeedContract.View view;
    private Intent intent;

    public FeedContentPresenterImpl(FeedContract.View view) {
        this.view = view;
    }
    @Override
    public void setFragment(Fragment fragment) {
        Log.d(TAG, "setFragment() 호출" + fragment.toString());
        view.addFragment(fragment);
    }

    @Override
    public void setActivity(Activity activity) {
        Log.d(TAG, "moveFragment() 호출" + activity.toString());

        view.moveActivity(activity);
    }
}
