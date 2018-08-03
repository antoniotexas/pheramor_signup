package com.example.pheramorsignup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;

public class UploadPictureActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 3;
    Toolbar  toolbar;
    ImageButton profileImageBtn;
    Button nextBtn;

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
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        profileImageBtn = findViewById(R.id.profile_image);
        nextBtn = findViewById(R.id.next_button);

        setToolbar();
        receivedData();
        sendData();

        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Upload Profile Picture");
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
            race = extras.getString("race");
            religion = extras.getString("religion");
        }
    }

    public void sendData(){

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isUriValid()){
                    return;
                }

                Intent intent = new Intent(getBaseContext(), SummaryActivity.class);
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
                intent.putExtra("pictureUri",uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            uri = selectedImageUri.toString();
        }
    }

    public boolean isUriValid(){

        boolean valid;

        if(uri != null && !uri.equals("")){
            valid = true;
        }else{
            valid = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadPictureActivity.this);
            builder.setMessage("Please choose a picture.")
                    .setTitle("Profile Picture")
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) { }
                    })
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            builder.create().show();
        }
        return valid;
    }
}
