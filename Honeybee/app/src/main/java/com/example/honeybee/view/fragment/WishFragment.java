package com.example.honeybee.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.honeybee.R;
import com.example.honeybee.model.UserData;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.adapter.WishAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishFragment extends Fragment {
    private static final String TAG = "WishFragment.class";
    private String u_id = "김현지";

    private RecyclerView recyclerView;
    private WishAdapter wishAdapter;

    private ArrayList<String> picked_user_image = new ArrayList<>();

    private ArrayList<String> pick_person;

    public WishFragment() {

    }

    public static WishFragment newInstance() {
        Log.d(TAG, "newInstance() 호출");
        Bundle bundle = new Bundle();
        WishFragment fragment = new WishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "WishFragment onCreateView() called");
        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        init(view);
        getWishList();


        recyclerView.setAdapter(wishAdapter);
        return view;
    }

    public void init(View view ) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        wishAdapter = new WishAdapter();
    }

    public void getWishList() {
        picked_user_image = new ArrayList<>();
        Call<UserData> userDataCall = NetRetrofit.getInstance().getRetrofitService().userDataFindById(u_id);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                UserData userData = response.body();
                assert userData != null;
                pick_person = userData.getPick_person();
                Log.d(TAG, "찜 목록 = " + pick_person);

                for (String pickPerson : pick_person) {
                    Call<UserData> pickedUserDataCall = NetRetrofit.getInstance().getRetrofitService().userDataFindById(pickPerson);
                    pickedUserDataCall.enqueue(new Callback<UserData>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<UserData> call, Response<UserData> response) {
                            UserData pickedUserData = response.body();
                            String user_image = pickedUserData.getUser_image().get(0);
                            wishAdapter.addItem(user_image);
                            Log.d(TAG, "찜 목록에 추가된 유저들의 프로필 사진 = " + picked_user_image);
                        }

                        @Override
                        public void onFailure(Call<UserData> call, Throwable t) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {

            }
        });
    }




}