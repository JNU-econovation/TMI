package com.example.honeybee.contract;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public interface FeedContract {
    interface View {
        void addFragment(Fragment fragment);

        void moveActivity(Activity activity);
    }

    interface Presenter {
        void setFragment(Fragment fragment);

        void setActivity(Activity activity);
    }
}
