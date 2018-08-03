package com.example.pheramorsignup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GenderDobActivity extends AppCompatActivity {

    Button nextBtn;
    Spinner genderSpinner;
    EditText dobField;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

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
        setContentView(R.layout.activity_gender_dob);

        nextBtn = findViewById(R.id.next_button);
        dobField = findViewById(R.id.dob_field);
        genderSpinner= findViewById(R.id.gender_spinner);

        toolbar();
        receivedData();
        setGender();
        getCalender();

        dobField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GenderDobActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        sendData();
    }

    public void toolbar(){
        Toolbar my_toolbar = findViewById(R.id.navigation_bar);
        setTitle("Personal Information");
        setSupportActionBar(my_toolbar);
        my_toolbar.setTitleTextColor(getResources().getColor(R.color.dark_blue));
    }

    public void receivedData(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            email    = extras.getString("email");
            password = extras.getString("password");
            name     = extras.getString("name");
            zip      = extras.getString("zip");
            height   = extras.getString("height");
        }
    }

    public void setGender(){

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myGender = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setAge(){
        age = Integer.toString(getAge(dobField.getText().toString()));
    }

    public void sendData(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAge();

                Intent intent = new Intent(getBaseContext(), GenderInterestActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("name",name);
                intent.putExtra("zip",zip);
                intent.putExtra("height",height);
                intent.putExtra("myGender",myGender);
                intent.putExtra("age",age);

                startActivity(intent);
            }
        });
    }

    public void getCalender(){
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDobField();
            }
        };
    }

    private void updateDobField() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dobField.setText(sdf.format(myCalendar.getTime()));
    }

    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }
}

