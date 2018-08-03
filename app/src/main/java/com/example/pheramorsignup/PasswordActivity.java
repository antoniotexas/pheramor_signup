package com.example.pheramorsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity {

    Button   nextBtn;
    EditText passwordField;
    Toolbar  toolbar;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        passwordField  = findViewById(R.id.password_field);
        nextBtn        = findViewById(R.id.next_button);

        setToolbar();
        receivedData();
        sendData();
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Sign Up");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
    }

    public void receivedData(){
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            email = extras.getString("email");
        }
    }

    public void sendData(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordValid()) {
                    Intent intent = new Intent(getBaseContext(), NameActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }
            }
        });
    }

    //Password should not be empty
    //Password should contain at least 4 characters,
    //Password should contain at least 1 upper case character,
    //at least 1 numeric value,
    //and one special character
    public boolean isPasswordValid(){

        Pattern pattern;
        Matcher matcher;
        boolean valid;

        password = passwordField.getText().toString();
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        pattern  = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        if(! TextUtils.isEmpty(password) && matcher.matches()){
            valid = true;
        }else{
            passwordField.setError("Minimum 4 characters, 1 upper case character, 1 numeric character, 1 special character");
            valid = false;
        }
        return valid;
    }
}