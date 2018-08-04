package com.example.pheramorsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nameField;
    EditText zipCodeField;
    EditText footField;
    EditText inchesField;
    Button nextBtn;

    String email;
    String password;
    String name;
    String zipCode;
    String foot;
    String inches;
    String height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        nameField = findViewById(R.id.name_field);
        zipCodeField = findViewById(R.id.zip_code_field);
        footField = findViewById(R.id.foot_field);
        inchesField = findViewById(R.id.inches_field);
        nextBtn = findViewById(R.id.next_button);

        setToolbar();
        receivedData();
        sendData();
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Personal Information");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
    }

    public void receivedData(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            email = extras.getString("email");
            password = extras.getString("password");
        }
    }

    public void sendData(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFormValid()) {
                    return;
                }
                Intent intent = new Intent(getBaseContext(), GenderDobActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("name",name);
                intent.putExtra("zip",zipCode);
                intent.putExtra("height", foot + "'" + inches + "\"");
                startActivity(intent);
            }
        });
    }

    public boolean isFormValid(){

        Pattern zipPattern;
        Matcher zipMatcher;
        boolean valid = true;

        name    = nameField.getText().toString();
        zipCode = zipCodeField.getText().toString();
        foot    = footField.getText().toString();
        inches  = inchesField.getText().toString();

        final String ZIP_PATTERN = "^[0-9]{5}(?:-[0-9]{4})?$";
        zipPattern  = Pattern.compile(ZIP_PATTERN);
        zipMatcher = zipPattern .matcher(zipCode);

        if(TextUtils.isEmpty(name)){
            nameField.setError("Should not be empty");
            valid = false;
        }
        if(TextUtils.isEmpty(zipCode) || !zipMatcher.matches() ){
            zipCodeField.setError("Not a valid zip code");
            valid = false;
        }

        //TODO: Check if the input is numeric and if the height is within the correct range.
        // 1 foot = 12 inches
        if(TextUtils.isEmpty(foot)){
            footField.setError("Should not be empty");
            valid = false;
        }
        if( TextUtils.isEmpty(inches)){
            inchesField.setError("Should not be empty");
            valid = false;
        }

        return valid;
    }
}
