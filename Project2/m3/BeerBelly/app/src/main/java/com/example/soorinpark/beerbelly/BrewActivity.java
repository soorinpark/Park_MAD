package com.example.soorinpark.beerbelly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.soorinpark.beerbelly.adapter.BreweriesAdapter;
import com.example.soorinpark.beerbelly.model.Brewery;
import com.example.soorinpark.beerbelly.model.BreweryList;
import com.example.soorinpark.beerbelly.rest.ApiClient;
import com.example.soorinpark.beerbelly.rest.ApiInterface;
import com.example.soorinpark.beerbelly.ui.DividerItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by soorinpark on 11/10/16.
 */

public class BrewActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "546e79849610632a56e3ea49a776f1ba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brewery_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.brewery_recycler_layout);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<BreweryList> call = apiService.getLocationBrews(API_KEY, "80302");
        call.enqueue(new Callback<BreweryList>() {

            @Override
            public void onResponse(Call<BreweryList> call, Response<BreweryList> response) {
                List<Brewery> brews = response.body().getDataList();
                recyclerView.setAdapter(new BreweriesAdapter(brews, R.layout.brewery, getApplicationContext()));
                Log.d(TAG, "Number of breweries received: " + brews.size());
            }

            @Override
            public void onFailure(Call<BreweryList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }
}
