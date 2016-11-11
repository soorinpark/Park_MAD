package com.example.soorinpark.beerbelly.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.soorinpark.beerbelly.R;
import com.example.soorinpark.beerbelly.adapter.BreweriesAdapter;
import com.example.soorinpark.beerbelly.model.Brewery;
import com.example.soorinpark.beerbelly.model.BreweryList;
import com.example.soorinpark.beerbelly.rest.ApiClient;
import com.example.soorinpark.beerbelly.rest.ApiInterface;
import com.example.soorinpark.beerbelly.ui.DividerItemDecoration;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by soorinpark on 11/10/16.
 */

public class BrewActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "546e79849610632a56e3ea49a776f1ba";

    private String zipValue = null;
    private String cityValue = null;
    private String stateValue = null;

    private GoogleMap mMap;

    private List<Brewery> brews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brewery_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.brewery_recycler_layout);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            zipValue = extras.getString("zipcode");
            cityValue = extras.getString("city");
            stateValue = extras.getString("state");
        }

        Log.d("zcs", zipValue + "-" + cityValue + "-" + stateValue);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        if (!zipValue.matches("")) {
            Call<BreweryList> call = apiService.getBrewZip(API_KEY, zipValue);
            call.enqueue(new Callback<BreweryList>() {
                @Override
                public void onResponse(Call<BreweryList> call, Response<BreweryList> response) {
                    brews = response.body().getDataList();
                    addMarker();
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

        else {
            Call<BreweryList> call = apiService.getBrewCityState(API_KEY, cityValue, stateValue);
            call.enqueue(new Callback<BreweryList>() {
                @Override
                public void onResponse(Call<BreweryList> call, Response<BreweryList> response) {
                    brews = response.body().getDataList();
                    addMarker();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void addMarker(){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i=0; i < brews.size(); i++) {
            Double lat = brews.get(i).getLatitude();
            Double lng = brews.get(i).getLongitude();
            LatLng latlong = new LatLng(lat, lng);
            builder.include(latlong);
            mMap.addMarker(new MarkerOptions().position(latlong).title(brews.get(i).getName()));

        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
    }
}
