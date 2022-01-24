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
import com.example.honeybee.view.WishSubscriber;
import com.example.honeybee.view.adapter.WishAdapter;
import com.example.honeybee.view.layout.WishItemDecoration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
    private Observable<String> observable;

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
        recyclerView.addItemDecoration(new WishItemDecoration(getContext()));
        wishAdapter = new WishAdapter();
    }

    public void getWishList() {
        picked_user_image = new ArrayList<>();
        WishSubscriber<String> wishSubscriber = new WishSubscriber<String>();

        Call<UserData> userDataCall = NetRetrofit.getInstance().getRetrofitService().userDataFindById(u_id);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                UserData userData = response.body();
                assert userData != null;
                pick_person = userData.getPick_person();
                Log.d(TAG, "찜 목록 = " + pick_person);

//                Observable<String> source = Observable.fromIterable(pick_person);
//                source.subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) { }
//                    @Override
//                    public void onNext(@NonNull String s) {
//                        Log.d("비동기처리", "찜 목록에 있는 사용자 outside of enqueue =" + s);
//                        Observable<String> source2 = Observable.just(s);
//                        source2.subscribe(new Observer<String>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(@NonNull String s) {
//                                Call<UserData> pickedUserDataCall = NetRetrofit.getInstance().getRetrofitService().userDataFindById(s);
//                                pickedUserDataCall.enqueue(new Callback<UserData>() {
//                                    @SuppressLint("NotifyDataSetChanged")
//                                    @Override
//                                    public void onResponse(Call<UserData> call, Response<UserData> response) {
//                                        UserData pickedUserData = response.body();
//                                        String user_image = pickedUserData.getUser_image().get(0);
//                                        Log.d("비동기처리", "찜 목록에 있는 사용자 inside of enqueue=" + s);
//                                        Log.d(TAG, "user_image = " + user_image);
//                                        wishAdapter.addItem(user_image);
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<UserData> call, Throwable t) {
//                                        Log.d(TAG, t.getMessage());
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//
//                        // keep
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
                for (String pickPerson : pick_person) {
                    try {
                        Thread.sleep(50);
                        Call<UserData> pickedUserDataCall = NetRetrofit.getInstance().getRetrofitService().userDataFindById(pickPerson);
                        pickedUserDataCall.enqueue(new Callback<UserData>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<UserData> call, Response<UserData> response) {

                                UserData pickedUserData = response.body();
                                String user_image = pickedUserData.getUser_image().get(0);
                                Log.d(TAG, "pickPerson =" + pickPerson);
                                Log.d(TAG, "user_image = " + user_image);
//                                wishAdapter.addItem(user_image);
                                observable = Observable.just(user_image);
                                observable.subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull String s) {
                                        Log.d(TAG, "onNext() item =" + s);
                                        wishAdapter.addItem(s);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<UserData> call, Throwable t) {
                                Log.d(TAG, t.getMessage());
                            }
                        });
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {

            }
        });
    }




}