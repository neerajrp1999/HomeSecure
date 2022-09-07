package com.example.automationapp.homesecure;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SeeImage extends AppCompatActivity {
    int ActiveImage;
    ImageView image;
    TextView label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_image);
        image=(ImageView)findViewById(R.id.image);
        label=(TextView)findViewById(R.id.label);
        ActiveImage=0;
        new firebaseDB(getApplicationContext()).LoadImageOfSeeImage(image,label,ActiveImage);
    }
    public void OLDPIC(View view){
        ActiveImage=ActiveImage+1;
        if(ActiveImage==10){
            ActiveImage=ActiveImage%10;
            Toast.makeText(this,"You cant load after 10 pic",Toast.LENGTH_SHORT).show();
        }
        new firebaseDB(getApplicationContext()).LoadImageOfSeeImage(image,label,ActiveImage);
    }
    public void NEWPIC(View view){
            if(ActiveImage==0){
                Toast.makeText(this,"No New pic avilable",Toast.LENGTH_SHORT).show();
                return;
            }
            ActiveImage=-1;
            String ActiveImageNo=String.valueOf(ActiveImage);
            new firebaseDB(getApplicationContext()).LoadImageOfSeeImage(image,label,ActiveImage);
    }
}