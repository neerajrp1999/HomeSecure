package com.example.automationapp.homesecure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences settings;
    TextView label,time;
    ImageView requestedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        label=(TextView)findViewById(R.id.label);
        time=(TextView)findViewById(R.id.time);
        requestedImage=(ImageView)findViewById(R.id.requestedImage);
        new firebaseDB(getApplicationContext()).getRequest(label,time,requestedImage);
    }
    public void RejectRequest(View view){
        new firebaseDB(getApplicationContext()).updateDRequest("accepted","0");
        new firebaseDB(getApplicationContext()).updateDRequest("requested","0");
        label.setText("No one requested");
        requestedImage.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        Toast.makeText(this,"All request rejected",Toast.LENGTH_SHORT).show();
    }
    public void AcceptRequest(View view){
        new firebaseDB(getApplicationContext()).updateDRequest("accepted","1");
        label.setText("Request accepted");
        requestedImage.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        Toast.makeText(this,"All request Accepted",Toast.LENGTH_SHORT).show();
    }
    public void ShowActivity(View view){
        Intent intent = new Intent(this, SeeImage.class);
        startActivity(intent);
    }
}