package com.example.soorinpark.beerbelly.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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



    private String[] styleArray;
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

        this.styleArray = new String[]{
                "",
                "Classic English-Style Pale Ale",
                "English-Style India Pale Ale",
                "Ordinary Bitter",
                "Special Bitter or Best Bitter",
                "Extra Special Bitter",
                "English-Style Summer Ale",
                "Scottish-Style Light Ale",
                "Scottish-Style Heavy Ale",
                "Scottish-Style Export Ale",
                "English-Style Pale Mild Ale",
                "English-Style Dark Mild Ale",
                "English-Style Brown Ale",
                "Old Ale",
                "Strong Ale",
                "Scotch Ale",
                "British-Style Imperial Stout",
                "British-Style Barley Wine Ale",
                "Brown Porter",
                "Robust Porter",
                "Sweet or Cream Stout",
                "Oatmeal Stout",
                "Irish-Style Red Ale",
                "Classic Irish-Style Dry Stout",
                "Foreign (Export)-Style Stout",
                "American-Style Pale Ale",
                "Fresh \"Wet\" Hop Ale",
                "Pale American-Belgo-Style Ale",
                "Dark American-Belgo-Style Ale",
                "American-Style Strong Pale Ale",
                "American-Style India Pale Ale",
                "Imperial or Double India Pale Ale",
                "American-Style Amber/Red Ale",
                "Imperial Red Ale",
                "American-Style Barley Wine Ale",
                "American-Style Wheat Wine Ale",
                "Golden or Blonde Ale",
                "American-Style Brown Ale",
                "Smoke Porter",
                "Brett Beer",
                "American-Style Sour Ale",
                "American-Style Black Ale",
                "American-Style Stout",
                "American-Style Imperial Stout",
                "Specialty Stouts",
                "German-Style Ku00f6lsch / Ku00f6ln-Style Ku00f6lsch",
                "Berliner-Style Weisse (Wheat)",
                "Leipzig-Style Gose",
                "South German-Style Hefeweizen / Hefeweissbier",
                "South German-Style Kristall Weizen / Kristall Weissbier",
                "German-Style Leichtes Weizen / Weissbier",
                "South German-Style Bernsteinfarbenes Weizen / Weissbier",
                "South German-Style Dunkel Weizen / Dunkel Weissbier",
                "South German-Style Weizenbock / Weissbock",
                "Bamberg-Style Weiss (Smoke) Rauchbier (Dunkel or Helles)",
                "German-Style Altbier",
                "Kellerbier (Cellar beer) or Zwickelbier - Ale",
                "Belgian-Style Flanders Oud Bruin or Oud Red Ales",
                "Belgian-Style Dubbel",
                "Belgian-Style Tripel",
                "Belgian-Style Quadrupel",
                "Belgian-Style Blonde Ale",
                "Belgian-Style Pale Ale",
                "Belgian-Style Pale Strong Ale",
                "Belgian-Style Dark Strong Ale",
                "Belgian-Style White (or Wit) / Belgian-Style Wheat",
                "Belgian-Style Lambic",
                "Belgian-Style Gueuze Lambic",
                "Belgian-Style Fruit Lambic",
                "Belgian-Style Table Beer",
                "Other Belgian-Style Ales",
                "French-Style Biu00e8re de Garde",
                "French & Belgian-Style Saison",
                "International-Style Pale Ale",
                "Australian-Style Pale Ale",
                "German-Style Pilsener",
                "Bohemian-Style Pilsener",
                "German-Style Leichtbier",
                "Mu00fcnchner (Munich)-Style Helles",
                "Dortmunder / European-Style Export",
                "Vienna-Style Lager",
                "German-Style Mu00e4rzen",
                "German-Style Oktoberfest / Wiesen (Meadow)",
                "European-Style Dark / Mu00fcnchner Dunkel",
                "German-Style Schwarzbier",
                "Bamberg-Style Mu00e4rzen Rauchbier",
                "Bamberg-Style Helles Rauchbier",
                "Bamberg-Style Bock Rauchbier",
                "Traditional German-Style Bock",
                "German-Style Heller Bock/Maibock",
                "German-Style Doppelbock",
                "German-Style Eisbock",
                "Kellerbier (Cellar beer) or Zwickelbier - Lager",
                "American-Style Lager",
                "American-Style Light (Low Calorie) Lager",
                "American-Style Low-Carbohydrate Light Lager",
                "American-Style Amber (Low Calorie) Lager",
                "American-Style Premium Lager",
                "American-Style Pilsener",
                "American-Style Ice Lager",
                "American-Style Malt Liquor",
                "American-Style Amber Lager",
                "American-Style Mu00e4rzen / Oktoberfest",
                "American-Style Dark Lager",
                "Baltic-Style Porter",
                "Australasian, Latin American or Tropical-Style Light Lager",
                "International-Style Pilsener",
                "Dry Lager",
                "Session Beer",
                "American-Style Cream Ale or Lager",
                "California Common Beer",
                "Ginjo Beer or Sake-Yeast Beer",
                "Light American Wheat Ale or Lager with Yeast",
                "Light American Wheat Ale or Lager without Yeast",
                "Fruit Wheat Ale or Lager with or without Yeast",
                "Dark American Wheat Ale or Lager with Yeast",
                "Dark American Wheat Ale or Lager without Yeast",
                "Rye Ale or Lager with or without Yeast",
                "German-Style Rye Ale (Roggenbier) with or without Yeast",
                "Fruit Beer",
                "Field Beer",
                "Pumpkin Beer",
                "Chocolate / Cocoa-Flavored Beer",
                "Coffee-Flavored Beer",
                "Herb and Spice Beer",
                "Specialty Beer",
                "Specialty Honey Lager or Ale",
                "Gluten-Free Beer",
                "Indigenous Beer (Lager or Ale)",
                "Smoke Beer (Lager or Ale)",
                "Experimental Beer (Lager or Ale)",
                "Historical Beer",
                "Wood- and Barrel-Aged Beer",
                "Wood- and Barrel-Aged Pale to Amber Beer",
                "Wood- and Barrel-Aged Dark Beer",
                "Wood- and Barrel-Aged Strong Beer",
                "Wood- and Barrel-Aged Sour Beer",
                "Aged Beer (Ale or Lager)",
                "Other Strong Ale or Lager",
                "Non-Alcoholic (Beer) Malt Beverages",
                "Dry Mead",
                "Semi-Sweet Mead",
                "Sweet Mead",
                "Cyser (Apple Melomel)",
                "Pyment (Grape Melomel)",
                "Other Fruit Melomel",
                "Metheglin",
                "Braggot",
                "Open Category Mead",
                "Common Cider",
                "English Cider",
                "French Cider",
                "Common Perry",
                "Traditional Perry",
                "New England Cider",
                "Fruit Cider",
                "Apple Wine",
                "Other Specialty Cider or Perry",
                "American-Style Imperial Porter",
                "Adambier",
                "Grodziskie",
                "Flavored Malt Beverage",
                "Energy Enhanced Malt Beverage",
                "Double Red Ale",
                "Session India Pale Ale",
                "Contemporary Gose",
                "Dutch-Style Kuit, Kuyt or Koyt",
                "Belgian-style Fruit Beer",
                "Chili Pepper Beer",
                "Mixed Culture Brett Beer",
                "Wild Beer"

        };
        Spinner categorySpinner = (Spinner) findViewById(R.id.beerCategory);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, styleArray);
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

        Intent intent = new Intent(MainActivity.this, BrewActivity.class);
        intent.putExtra("zipcode", zipcodeText);
        intent.putExtra("city", cityText);
        intent.putExtra("state", stateText);
        startActivity(intent);
    }

}
