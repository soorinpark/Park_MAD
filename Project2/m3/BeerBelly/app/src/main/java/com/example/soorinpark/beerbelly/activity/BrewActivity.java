package com.example.soorinpark.beerbelly.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.soorinpark.beerbelly.R;
import com.example.soorinpark.beerbelly.adapter.BreweriesAdapter;
import com.example.soorinpark.beerbelly.model.Beer;
import com.example.soorinpark.beerbelly.model.BeerList;
import com.example.soorinpark.beerbelly.model.Brewery;
import com.example.soorinpark.beerbelly.model.BreweryList;
import com.example.soorinpark.beerbelly.rest.ApiClient;
import com.example.soorinpark.beerbelly.rest.ApiInterface;
import com.example.soorinpark.beerbelly.ui.DividerItemDecoration;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
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
    private Boolean useCurrent = false;

    private RecyclerView recyclerView;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private SupportMapFragment mapFragment;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_LOCATION = 2;
    private String provider;
    private Criteria criteria;
    private String permission = Manifest.permission.ACCESS_FINE_LOCATION;

    private LocationManager locationManager;
    private Location location;



    private Double lat;
    private Double lng;


    private List<Brewery> brews;
    private List<List<Beer>> beers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brewery_list);

        recyclerView = (RecyclerView) findViewById(R.id.brewery_recycler_layout);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            zipValue = extras.getString("zipcode");
            cityValue = extras.getString("city");
            stateValue = extras.getString("state");
            useCurrent = extras.getBoolean("current");
        }

        Log.d("zcs", zipValue + "-" + cityValue + "-" + stateValue);
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        if (!zipValue.matches("")) {
            Call<BreweryList> call = apiService.getBrewZip(API_KEY, zipValue);
            call.enqueue(new Callback<BreweryList>() {
                @Override
                public void onResponse(Call<BreweryList> call, Response<BreweryList> response) {
                    brews = response.body().getDataList();
                    getBeerInfo();
                    addMarker();
                    recyclerView.setAdapter(new BreweriesAdapter(brews, R.layout.brewery, getApplicationContext()));
                    Log.d(TAG, "Number of breweries received: " + brews.size());
                }

                @Override
                public void onFailure(Call<BreweryList> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
        // if current location
        if (useCurrent == true) {
            Log.d(":)", "??");

        } else {
            Call<BreweryList> call = apiService.getBrewCityState(API_KEY, cityValue, stateValue);
            call.enqueue(new Callback<BreweryList>() {
                @Override
                public void onResponse(Call<BreweryList> call, Response<BreweryList> response) {
                    brews = response.body().getDataList();
                    getBeerInfo();
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
        if (useCurrent == true) {
            Log.d("current", "current");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("req", "requesting");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
                return;
            } else {
                location = getLastKnownLocation();

                //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.d("??", "hm");
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    Log.d("lat", String.valueOf(lat));
                    Log.d("long", String.valueOf(lng));
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        }
        else{
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    public void addMarker() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < brews.size(); i++) {
            Double lat = brews.get(i).getLatitude();
            Double lng = brews.get(i).getLongitude();
            LatLng latlong = new LatLng(lat, lng);
            builder.include(latlong);
            mMap.addMarker(new MarkerOptions().position(latlong).title(brews.get(i).getName()));

        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    public void getBeerInfo() {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        for (int i = 0; i < brews.size(); i++) {
            String brewId = brews.get(i).getBreweryId();
            Call<BeerList> call = apiService.getBeerFromBrew(brewId, API_KEY);
            call.enqueue(new Callback<BeerList>() {
                @Override
                public void onResponse(Call<BeerList> call, Response<BeerList> response) {
                    List<Beer> beerTemp = response.body().getBeerList();
                    beers.add(beerTemp);
                    Log.d(TAG, "Number of beers received: " + beers.size());
                }

                @Override
                public void onFailure(Call<BeerList> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}

