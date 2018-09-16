package com.deliverystuff.ui;


import com.deliverystuff.model.ModelDelivery;
import com.deliverystuff.networking.NetworkError;
import com.deliverystuff.networking.Service;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getCityList(int currentPage) {
        view.showWait();

        Subscription subscription = service.getDeliveries(new Service.GetCityListCallback() {
            @Override
            public void onSuccess(List<ModelDelivery> cityListResponse) {
                view.removeWait();
                view.getDeliveriesListSuccess(cityListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        }, currentPage, 10);
        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
