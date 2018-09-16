package com.deliverystuff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deliverystuff.R;
import com.deliverystuff.model.ModelDelivery;
import com.deliverystuff.networking.Service;

import java.util.List;

import javax.inject.Inject;

import static com.deliverystuff.ui.LocationActivity.EXTRA_MODEL_DELIVERY;

public class HomeActivity extends BaseApp implements HomeView {

    private RecyclerView list;
    @Inject
    public Service service;
    private ProgressBar progressBar;
    private HomeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;
    private HomePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        presenter = new HomePresenter(service, this);
        presenter.getCityList(currentPage);
    }

    public void renderView() {
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setTitle(R.string.deliveries);
        linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);
        adapter = new HomeAdapter(getApplicationContext(),
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(ModelDelivery item, View view) {
                        Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
                        intent.putExtra(EXTRA_MODEL_DELIVERY, item);
                        Pair<View, String> p1 = Pair.create(view.findViewById(R.id.imgLogo), "imgLogo");
                        Pair<View, String> p2 = Pair.create(view.findViewById(R.id.tvDescription), "tvDescription");
                        Pair<View, String> p3 = Pair.create(view.findViewById(R.id.tvLocationName), "tvLocationName");
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(HomeActivity.this, p1, p2, p3);
                        startActivity(intent, options.toBundle());
                    }
                });
        list.setAdapter(adapter);
        list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLastPage) {
                    isLoading = true;
                    currentPage += 10;
                    showWait();
                    presenter.getCityList(currentPage);
                }
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(this, appErrorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDeliveriesListSuccess(List<ModelDelivery> modelDeliveryList) {
        if (modelDeliveryList != null && modelDeliveryList.size() > 0) {
            adapter.setData(modelDeliveryList);
        } else {
            isLastPage = true;
        }
    }
}