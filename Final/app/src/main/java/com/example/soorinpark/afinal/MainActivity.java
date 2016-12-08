package com.example.soorinpark.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText iceCreamEditText;
    Spinner iceCreamSpinner;
    Switch dairySwitch;
    ToggleButton cupToggle;
    RadioGroup radioGroup;
    CheckBox sprinkles;
    CheckBox hotfudge;
    CheckBox nuts;
    Button treatme;
    Button findtreat;
    TextView icecreamSummary;
    ImageView icecreamImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iceCreamEditText = (EditText) findViewById(R.id.nameEditText);
        iceCreamSpinner = (Spinner) findViewById(R.id.icSpinner);
        dairySwitch = (Switch) findViewById(R.id.dairySwitch);
        cupToggle = (ToggleButton) findViewById(R.id.cupToggleButton);
        radioGroup = (RadioGroup) findViewById(R.id.icRadioGroup);
        sprinkles = (CheckBox) findViewById(R.id.sprinklesCB);
        hotfudge = (CheckBox) findViewById(R.id.hotFudgeCB);
        nuts = (CheckBox) findViewById(R.id.nutsCB);
        treatme = (Button) findViewById(R.id.treatMeButton);
        findtreat = (Button) findViewById(R.id.findTreatButton);
        icecreamSummary = (TextView) findViewById(R.id.icSummary);
        icecreamImage = (ImageView) findViewById(R.id.icImage);

        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Chocolate");
        spinnerArray.add("Salted Caramel");
        spinnerArray.add("Cookies and Cream");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        iceCreamSpinner.setAdapter(spinnerArrayAdapter);

        radioGroup.check(R.id.icRadioButton);

        cupToggle.setText("Cone");
        cupToggle.setTextOff("Cone");
        cupToggle.setTextOn("Cup");

        treatme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String iceCreamName = iceCreamEditText.getText().toString();

                String dairyChecked = "";
                if (dairySwitch.isChecked()) {
                    dairyChecked = "dairy-free";
                }

                String cupOrCone;
                if (cupToggle.isChecked()) {
                    cupOrCone = "cup";
                }
                else {
                    cupOrCone = "cone";
                }

                String flavor = iceCreamSpinner.getSelectedItem().toString();
                if (flavor == "Chocolate") {
                    icecreamImage.setImageResource(R.drawable.chocolate);
                }
                else if (flavor == "Salted Caramel") {
                    icecreamImage.setImageResource(R.drawable.caramel);
                }
                else {
                    icecreamImage.setImageResource(R.drawable.cookiescream);
                }

                int radioIndex = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(radioIndex);
                String iceCreamType = (String) radioButton.getText();

                String Toppings = "";
                String sprinklesText = "";
                String hotfudgeText = "";
                String nutsText = "";

                if (sprinkles.isChecked()) {
                    sprinklesText = "sprinkles";
                }
                if (hotfudge.isChecked()) {
                    hotfudgeText = "hot fudge";
                }
                if (nuts.isChecked()) {
                    nutsText = "nuts";
                }

                if (!sprinkles.isChecked() && !hotfudge.isChecked() && !nuts.isChecked()) {
                    Toppings = "none";
                }
                else {
                    Toppings = sprinklesText + " "+ hotfudgeText + " " + nutsText;
                }

                icecreamSummary.setText("My " + iceCreamName + " is a " +  dairyChecked + " " + flavor + " " + iceCreamType + " " + cupOrCone + " with " + Toppings + ".");


            }
        });

        findtreat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

    }

}
