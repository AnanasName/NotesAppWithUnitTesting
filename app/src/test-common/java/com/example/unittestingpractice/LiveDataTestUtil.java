package com.example.unittestingpractice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil<T> {

    public T getValue(final LiveData<T> liveData) throws InterruptedException{

        final List<T> data = new ArrayList<>();

        final CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data.add(t);
                latch.countDown();
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);

        try{
            latch.await(2, TimeUnit.SECONDS);
        }catch(InterruptedException e){
            throw new InterruptedException("Latch failure");
        }

        if (data.size() > 0){
            return data.get(0);
        }
        return null;
    }
}
