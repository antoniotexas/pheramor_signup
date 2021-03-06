package com.example.pheramorsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button signUpBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        signUpBtn = findViewById(R.id.sign_up_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Pheramor");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
    }
}
