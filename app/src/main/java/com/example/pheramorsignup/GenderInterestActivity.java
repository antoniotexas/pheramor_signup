package com.example.pheramorsignup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

public class GenderInterestActivity extends AppCompatActivity {

    Toolbar  toolbar;
    CheckBox womanBox;
    CheckBox manBox;
    Button nextBtn;
    TextView ageRangeView;
    CrystalRangeSeekbar rangeSeekBar;

    String woman = "";
    String man = "";

    String minRangeValue;
    String maxRangeValue;

    String minAndMax;

    //For received data
    String email;
    String password;
    String name;
    String zip;
    String height;
    String myGender;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_interest);

        womanBox = findViewById(R.id.woman_check_box);
        manBox = findViewById(R.id.man_check_box);
        nextBtn = findViewById(R.id.next_button);
        ageRangeView = findViewById(R.id.age_range_view);
        rangeSeekBar = findViewById(R.id.rangeSeekbar1);

        setToolbar();
        receivedData();
        setRange();
        checkedBoxValue();
        sendData();
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Gender Interest");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
    }

    public void receivedData(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            email    = extras.getString("email");
            password = extras.getString("password");
            name     = extras.getString("name");
            zip      = extras.getString("zip");
            height   = extras.getString("height");
            myGender = extras.getString("myGender");
            age      = extras.getString("age");
        }
    }

    public void setRange(){
        // set listener
        rangeSeekBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minRangeValue = String.valueOf(minValue);
                maxRangeValue = String.valueOf(maxValue);
                String range = "Age Range (" + minRangeValue +" - " + maxRangeValue + ")";
                ageRangeView.setText(range);
                minAndMax = "between " + minRangeValue + " and " + maxRangeValue;
            }
        });
    }

    public void checkedBoxValue(){

        womanBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    woman = "Woman";
                    return;
                }
                woman = "";
            }
        });

        manBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    man = "Man";
                    return;
                }
                man = "";
            }
        });
    }

    public void sendData(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RaceReligionActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("name",name);
                intent.putExtra("zip",zip);
                intent.putExtra("height",height);
                intent.putExtra("myGender",myGender);
                intent.putExtra("age",age);
                intent.putExtra("genderInterest",man +" " + woman);
                intent.putExtra("ageRange",minAndMax);
                startActivity(intent);
            }
        });
    }
}
