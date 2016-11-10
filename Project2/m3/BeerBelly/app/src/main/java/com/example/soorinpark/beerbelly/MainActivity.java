package com.example.soorinpark.beerbelly;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.soorinpark.beerbelly.model.Brewery;
import com.example.soorinpark.beerbelly.model.BreweryList;
import com.example.soorinpark.beerbelly.rest.ApiClient;
import com.example.soorinpark.beerbelly.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by soorinpark on 11/8/16.
 */
/* you can get brewery data from the beer list. use the brewery id obtained from the beer list to
also match for a brewery in the local brewery list and if they overlap, return the
overlapping brewery as well as the beer type.
 */

public class MainActivity extends AppCompatActivity {

    String API = "http://api.brewerydb.com/v2/"; //BASE URL
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "546e79849610632a56e3ea49a776f1ba";

    private String[] categoryArray;
    private Integer zipCode = 80302;
    private String cityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.categoryArray = new String[] {
                "",
                "British Origin Ales",
                "Irish Origin Ales",
                "North American Origin Ales",
                "German Origin Ales",
                "Belgian And French Origin Ales",
                "International Ale Styles",
                "European-germanic Lager",
                "North American Lager",
                "Other Lager",
                "International Styles",
                "Hybrid/mixed Beer",
                "Mead, Cider, & Perry",
                "Other Origin",
                "Malternative Beverages",
                "Other"
        };
        Spinner categorySpinner = (Spinner) findViewById(R.id.beerCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryArray);
        categorySpinner.setAdapter(adapter);


        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/Bungee-Regular.ttf");
        Typeface bodyFont = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

        //BEER BELLY Title
        TextView titleTextView = (TextView) findViewById(R.id.textView);
        titleTextView.setTypeface(headFont);

        //I like...
        TextView likeTextView = (TextView) findViewById(R.id.textView2);
//        final EditText likeEditText = (EditText) findViewById(R.id.editText);
        likeTextView.setTypeface(headFont);
//        likeEditText.setTypeface(bodyFont);

        //I live in...
        TextView liveTextView = (TextView) findViewById(R.id.textView3);
        EditText liveEditText = (EditText) findViewById(R.id.editText2);
        liveTextView.setTypeface(headFont);
        liveEditText.setTypeface(bodyFont);

        //button
        Button drinkButton = (Button) findViewById(R.id.drinkButton);
        drinkButton.setTypeface(bodyFont);

    }

    public void changeView(View view)
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<BreweryList> call = apiService.getLocationBrews(API_KEY, zipCode);
        call.enqueue(new Callback<BreweryList>() {
            @Override
            public void onResponse(Call<BreweryList>call, Response<BreweryList> response) {
                List<Brewery> numBrews = response.body().getDataList();
                Log.d(TAG, "Number of breweries received: " + numBrews.size());
            }

            @Override
            public void onFailure(Call<BreweryList>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
