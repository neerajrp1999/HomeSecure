package com.example.automationapp.homesecure;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.content.Context.MODE_PRIVATE;

public class firebaseDB {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database;
    DatabaseReference myref ;
    Context ctx;
    firebaseDB(Context context) {
        this.ctx = context;
    }
    public void LoadImageOfSeeImage(final ImageView image, final TextView label, final int ActiveImageNo){
        DocumentReference user = db.collection("data").document("image");
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String l=null;
                    DocumentSnapshot doc = task.getResult();
                    if(ActiveImageNo==0){l = doc.get("0").toString();}
                    else if(ActiveImageNo==1){l = doc.get("1").toString();}
                    else if(ActiveImageNo==2){l = doc.get("2").toString();}
                    else if(ActiveImageNo==3){l = doc.get("3").toString();}
                    else if(ActiveImageNo==4){l = doc.get("4").toString();}
                    else if(ActiveImageNo==5){l = doc.get("5").toString();}
                    else if(ActiveImageNo==6){l = doc.get("6").toString();}
                    else if(ActiveImageNo==7){l = doc.get("7").toString();}
                    else if(ActiveImageNo==8){l = doc.get("8").toString();}
                    else if(ActiveImageNo==9){l = doc.get("9").toString();}
                    label.setText(l);
                    new firebaseDB(ctx).LoadImage2(image,l);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void updateDRequest(final String type, final String c)  {
        DocumentReference up = db.collection("data").document("request");
        up.update(type, c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }

    public void LoadRequesterImage(final TextView t, final ImageView image) {
        DocumentReference user = db.collection("data").document("requestedImage");
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String l = doc.get("1").toString();
                    t.setText(l);
                    new firebaseDB(ctx).LoadImage2(image,"requesterImage/"+l);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void LoadImage2(final ImageView image, String url) {
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference("image/"+url.trim());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final long ONE_MEGABYTE = 1024 * 1024;
        mImageRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        DisplayMetrics dm = new DisplayMetrics();
                        image.setMinimumHeight(dm.heightPixels);
                        image.setMinimumWidth(dm.widthPixels);
                        image.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void getRequest(final TextView l1,final TextView l2,final ImageView Im) {
        DocumentReference user = db.collection("data").document("request");
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String accepted= doc.get("accepted").toString();
                    String requested= doc.get("requested").toString();
                    if(accepted.equals("1")){
                        l1.setText("Request accepted");
                    }else if(requested.equals("1")){
                        l1.setText("Someone sended request.");
                        Im.setVisibility(View.VISIBLE);
                        l2.setVisibility(View.VISIBLE);
                        new firebaseDB(ctx).LoadRequesterImage(l2,Im);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}