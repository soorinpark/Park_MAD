package com.example.soorinpark.beerbelly;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Created by soorinpark on 11/8/16.
 */
/* you can get brewery data from the beer list. use the brewery id obtained from the beer list to
also match for a brewery in the local brewery list and if they overlap, return the
overlapping brewery as well as the beer type.
 */

public class MainActivity extends AppCompatActivity {

    String API = "http://api.brewerydb.com/v2/"; //BASE URL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Typeface headFont = Typeface.createFromAsset(getAssets(), "fonts/Bungee-Regular.ttf");
        Typeface bodyFont = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

        //BEER BELLY Title
        TextView titleTextView = (TextView) findViewById(R.id.textView);
        titleTextView.setTypeface(headFont);

        //I like...
        TextView likeTextView = (TextView) findViewById(R.id.textView2);
        final EditText likeEditText = (EditText) findViewById(R.id.editText);
        likeTextView.setTypeface(headFont);
        likeEditText.setTypeface(bodyFont);

        //I live in...
        TextView liveTextView = (TextView) findViewById(R.id.textView3);
        EditText liveEditText = (EditText) findViewById(R.id.editText2);
        liveTextView.setTypeface(headFont);
        liveEditText.setTypeface(bodyFont);

        //button
        Button drinkButton = (Button) findViewById(R.id.drinkButton);
        drinkButton.setTypeface(bodyFont);

        TextView tv = (TextView) findViewById(R.id.textView4);
    }

    public void changeView(View view)
    {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
