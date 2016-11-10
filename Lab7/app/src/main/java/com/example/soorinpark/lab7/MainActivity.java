package com.example.soorinpark.lab7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private EditText ageText;
    private ImageView ageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ageText = (EditText) findViewById(R.id.editText3);
        ageIcon = (ImageView) findViewById(R.id.imageView);


        ageText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int age = 0;

                if (!ageText.getText().toString().matches("")) {
                    age = Integer.parseInt(ageText.getText().toString());
                }
                if (age <= 1) {
                    ageIcon.setImageResource(R.drawable.baby);
                } else if (age > 1 && age <= 8) {
                    ageIcon.setImageResource(R.drawable.child);
                } else if (age > 8 && age <= 18) {
                    ageIcon.setImageResource(R.drawable.teen);
                } else if (age > 18 && age <= 30) {
                    ageIcon.setImageResource(R.drawable.young_adult);
                } else if (age > 30 && age <= 45) {
                    ageIcon.setImageResource(R.drawable.adult);
                } else if (age > 45 && age <= 60) {
                    ageIcon.setImageResource(R.drawable.middle_age);
                } else {
                    ageIcon.setImageResource(R.drawable.old);
                }


            }
        });
    }

}
