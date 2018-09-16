package com.deliverystuff.ui;

import com.deliverystuff.model.ModelDelivery;

import java.util.List;

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getDeliveriesListSuccess(List<ModelDelivery> modelDelivery);

}
