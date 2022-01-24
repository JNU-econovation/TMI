package com.example.honeybee.view;

import com.example.honeybee.view.adapter.WishAdapter;

import io.reactivex.Emitter;
import io.reactivex.subscribers.DisposableSubscriber;

public class WishSubscriber<String> implements Emitter<String> {
    private WishAdapter wishAdapter = new WishAdapter();

    @Override
    public void onNext(String string) {
        System.out.println(Thread.currentThread().getName() + " onNext( " + string + " )");
        wishAdapter.addItem((java.lang.String) string);
    }


    @Override
    public void onError(Throwable t) {
        System.out.println(Thread.currentThread().getName() + " onNext( " + t + " )");

    }

    @Override
    public void onComplete() {
    }
}
