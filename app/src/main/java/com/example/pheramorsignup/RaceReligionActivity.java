package com.example.pheramorsignup;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

public class RaceReligionActivity extends AppCompatActivity {

    Toolbar  toolbar;
    Spinner  raceSpinner;
    Spinner  religionSpinner;
    Button   nextBtn;

    String email;
    String password;
    String name;
    String zip;
    String height;
    String myGender;
    String age;
    String genderInterest;
    String ageRange;
    String race;
    String religion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_religion);

        nextBtn = findViewById(R.id.next_button);
        raceSpinner =  findViewById(R.id.race_spinner);
        religionSpinner =  findViewById(R.id.religion_spinner);

        setToolbar();
        receivedData();
        setRace();
        setReligion();
        sendData();

    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Personal Information");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark_blue));
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
            genderInterest = extras.getString("genderInterest");
            ageRange = extras.getString("ageRange");
        }
    }

    public void setRace(){

        ArrayAdapter<CharSequence> raceAdapter = ArrayAdapter.createFromResource(this,
                R.array.race_array, android.R.layout.simple_spinner_item);

        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        raceSpinner.setAdapter(raceAdapter);

        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                race = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void setReligion(){

        ArrayAdapter<CharSequence> ReligionAdapter = ArrayAdapter.createFromResource(this,
                R.array.religion_array, android.R.layout.simple_spinner_item);

        ReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        religionSpinner.setAdapter(ReligionAdapter);

        religionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                religion = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void sendData(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), UploadPictureActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("name",name);
                intent.putExtra("zip",zip);
                intent.putExtra("height",height);
                intent.putExtra("myGender",myGender);
                intent.putExtra("age",age);
                intent.putExtra("genderInterest",genderInterest);
                intent.putExtra("ageRange",ageRange);
                intent.putExtra("race",race);
                intent.putExtra("religion",religion);
                startActivity(intent);
            }
        });
    }
}
