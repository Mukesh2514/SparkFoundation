package com.example.sparkfoundation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Status_activity extends AppCompatActivity {
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "Hello123";
    TextView uname, uid;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_activity);
        uname=findViewById(R.id.textView3);
        uid=findViewById(R.id.textView4);
        img=findViewById(R.id.imageView3);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            uname.setText(personName);
            String personEmail = acct.getEmail();
            uid.setText(personEmail);
            Uri personImage = Uri.parse(String.valueOf(acct.getPhotoUrl()));
            Picasso.get().load(personImage).into(img);
//            String url=personImage.toString();
//            img.setImageURI(personImage);
            Log.d(TAG, "Currently Signed in: " + acct.getEmail()+"///"+ acct.getPhotoUrl());
        }

//        FirebaseUser user=firebaseAuth.getCurrentUser();

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Log Out Successfully...",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}