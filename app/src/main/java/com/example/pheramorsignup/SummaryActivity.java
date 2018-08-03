package com.example.pheramorsignup;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static java.net.Proxy.Type.HTTP;

public class SummaryActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView profilePicture;
    TextView genderField;
    TextView raceField;
    TextView religionField;
    TextView genderInterestField;
    TextView emailField;
    TextView passwordField;
    TextView nameField;
    TextView zipField;
    TextView heightField;
    TextView dobField;
    TextView ageRangeField;

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
    String pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        profilePicture = findViewById(R.id.profile_picture);
        nameField      = findViewById(R.id.name_holder);
        genderField    = findViewById(R.id.gender_holder);
        heightField    = findViewById(R.id.height_holder);
        raceField      = findViewById(R.id.race_holder);
        religionField  = findViewById(R.id.religion_holder);
        genderInterestField = findViewById(R.id.gender_interest_holder);
        zipField       = findViewById(R.id.location_holder);
        emailField     = findViewById(R.id.email_holder);
        passwordField  = findViewById(R.id.password_holder);
        dobField       = findViewById(R.id.dob_holder);
        ageRangeField  = findViewById(R.id.age_range);

        setToolbar();
        receivedData();
        new PostDataToServer().execute("https://external.dev.pheramor.com");
        summary();
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.navigation_bar);
        setTitle("Profile");
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
            genderInterest = extras.getString("genderInterest");
            ageRange = extras.getString("ageRange");
            race = extras.getString("race");
            religion = extras.getString("religion");
            pictureUri = extras.getString("pictureUri");
        }
    }

    public void summary(){
        emailField.setText(email);
        passwordField.setText(password);
        nameField.setText(name);
        zipField.setText(zip);
        heightField.setText(height);
        genderField.setText(myGender);
        dobField.setText(age);
        genderInterestField.setText(genderInterest);
        ageRangeField.setText(ageRange);
        raceField.setText(race);
        religionField.setText(religion);

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(pictureUri));
            profilePicture.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class PostDataToServer extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SummaryActivity.this,"Post",Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection;
            try {
                //Open a new URL connection
                connection = (HttpURLConnection) new URL(params[0])
                        .openConnection();

                //Defines a HTTP request type
                connection.setRequestMethod("POST");

                connection.setDoOutput(true);

                //Sets headers: Content-Type, Authorization
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                //Add POST data in JSON format

                JSONObject user = new JSONObject();

                try {
                    user.put("email",email);
                    user.put("password",password);
                    user.put("name",name);
                    user.put("zip code",zip);
                    user.put("height",height);
                    user.put("gender",myGender);
                    user.put("age",age);
                    user.put("gender interest",genderInterest);
                    user.put("age range",ageRange);
                    user.put("race",race);
                    user.put("religion",religion);
                    user.put("profile picture",pictureUri);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray usersArray = new JSONArray();
                usersArray.put(user);

                //Create a writer object and make the request
                OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
                outputStream.write(user.toString());
                outputStream.flush();
                outputStream.close();

                int respond = connection.getResponseCode();

                //Check for data in json array
                Log.d("Users Array",usersArray.toString());

                // Checking for request status
                //If 200, request was fulfilled.
                Log.d("Request status", Integer.toString(respond));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
