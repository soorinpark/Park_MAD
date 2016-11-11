package com.example.soorinpark.beerbelly.activity;

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

import com.example.soorinpark.beerbelly.R;

import java.io.IOException;


/**
 * Created by soorinpark on 11/8/16.
 */
/* you can get brewery data from the beer list. use the brewery id obtained from the beer list to
also match for a brewery in the local brewery list and if they overlap, return the
overlapping brewery as well as the beer type.
 */

public class MainActivity extends AppCompatActivity {



    private String[] categoryArray;
    private String[] stateArray;

    private String state;
    private Integer categoryID;
    private Boolean currentLocation;

    private EditText city;
    private EditText zipcode;
    private Spinner stateSpinner;

    private String cityText;
    private String zipcodeText;
    private String stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.categoryArray = new String[]{
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
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryArray);
        categorySpinner.setAdapter(categoryAdapter);

        this.stateArray = new String[]{
                "",
                "Alabama",
                "Arkansas",
                "Arizona",
                "Alaska",
                "California",
                "Colorado",
                "Connecticut",
                "Delaware",
                "Florida",
                "Georgia",
                "Hawaii",
                "Idaho",
                "Illinois",
                "Indiana",
                "Iowa",
                "Kansas",
                "Kentucky",
                "Louisiana",
                "Maine",
                "Maryland",
                "Massachusetts",
                "Michigan",
                "Minnesota",
                "Mississippi",
                "Missouri",
                "Montana",
                "Nebraska",
                "Nevada",
                "New Hampshire",
                "New Jersey",
                "New Mexico",
                "New York",
                "North Carolina",
                "North Dakota",
                "Ohio",
                "Oklahoma",
                "Oregon",
                "Pennsylvania",
                "Rhode Island",
                "South Carolina",
                "South Dakota",
                "Tennessee",
                "Texas",
                "Utah",
                "Vermont",
                "Virginia",
                "Washington",
                "West Virginia",
                "Wisconsin",
                "Wyoming"
        };
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stateArray);
        stateSpinner.setAdapter(stateAdapter);


        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/Bungee-Regular.ttf");
        Typeface bodyFont = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

        //BEER BELLY Title
        TextView titleTextView = (TextView) findViewById(R.id.textView);
        titleTextView.setTypeface(headFont);

        TextView likeTextView = (TextView) findViewById(R.id.textView2);
        likeTextView.setTypeface(headFont);

        TextView liveTextView = (TextView) findViewById(R.id.liveTextView);
        liveTextView.setTypeface(headFont);

        city = (EditText) findViewById(R.id.cityEditText);
        city.setTypeface(bodyFont);

        zipcode = (EditText) findViewById(R.id.zipEditText);
        zipcode.setTypeface(bodyFont);

        Button drinkButton = (Button) findViewById(R.id.drinkButton);
        drinkButton.setTypeface(bodyFont);

    }

    public void changeView(View view) throws IOException {

        cityText = city.getText().toString();
        zipcodeText = zipcode.getText().toString();
        stateText = stateSpinner.getSelectedItem().toString();

        if (cityText.matches("") && zipcodeText.matches("") && stateText.matches("")) {
            Log.d("current", "current location");
        }

        else {
            currentLocation = false;
        }

        Intent intent = new Intent(MainActivity.this, BrewActivity.class);
        intent.putExtra("zipcode", zipcodeText);
        intent.putExtra("city", cityText);
        intent.putExtra("state", stateText);
        startActivity(intent);
    }

}
