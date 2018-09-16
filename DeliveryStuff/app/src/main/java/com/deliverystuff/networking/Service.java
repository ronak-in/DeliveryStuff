package com.deliverystuff.networking;


import com.deliverystuff.data.SharedPrefsHelper;
import com.deliverystuff.model.ModelDelivery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    private final NetworkService networkService;
    private final SharedPrefsHelper sharedPrefsHelper;

    public Service(NetworkService networkService, SharedPrefsHelper sharedPrefsHelper) {
        this.networkService = networkService;
        this.sharedPrefsHelper = sharedPrefsHelper;
    }

    public Subscription getDeliveries(final GetCityListCallback callback, int offset, int limit) {

        return networkService.getDeliveries(offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<ModelDelivery>>>() {
                    @Override
                    public Observable<? extends List<ModelDelivery>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<ModelDelivery>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            ArrayList<ModelDelivery> deliveryArrayList = (ArrayList<ModelDelivery>) fromJson(sharedPrefsHelper.getData(),
                                    new TypeToken<ArrayList<ModelDelivery>>() {
                                    }.getType());
                            callback.onSuccess(deliveryArrayList);
                        } else
                            callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(List<ModelDelivery> deliveriesResponse) {
                        callback.onSuccess(deliveriesResponse);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(deliveriesResponse);
                        sharedPrefsHelper.setData(json);
                    }
                });
    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public interface GetCityListCallback {
        void onSuccess(List<ModelDelivery> cityListResponse);

        void onError(NetworkError networkError);
    }
}
