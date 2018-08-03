package com.example.pheramorsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    EditText emailField;
    Button   nextBtn;
    String   email;
    Toolbar  toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        emailField = findViewById(R.id.email_field);
        nextBtn    = findViewById(R.id.next_button);

        setToolbar();
        sendData();
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Sign Up");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
    }

    public boolean isEmailValid(){
        boolean valid = true;
        email = emailField.getText().toString();

        if (TextUtils.isEmpty(email) ) {
            emailField.setError("Should not be empty");
            valid = false;
        }else if(!email.contains("@") || !email.contains(".com") ){
            emailField.setError("Please enter an email");
            valid = false;
        }
        else {
            emailField.setError(null);
        }
        return valid;
    }

    public void sendData(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmailValid()) {
                    return;
                }
                Intent intent = new Intent(getBaseContext(), PasswordActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });
    }
}
