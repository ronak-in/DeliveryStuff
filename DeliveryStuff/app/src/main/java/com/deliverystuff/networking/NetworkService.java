package com.deliverystuff.networking;


import com.deliverystuff.model.ModelDelivery;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {

    @GET("deliveries")
    Observable<List<ModelDelivery>> getDeliveries(@Query("offset") int offset, @Query("limit") int limit);
}
