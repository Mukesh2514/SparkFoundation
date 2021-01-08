package com.example.sparkfoundation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CardView cd;
    ImageView img;
    TextView tv;
    Animation downToUp,upToDown;
    Button login, signUp, forgot;
    ImageButton facebook, twitter, linkedin;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cd = (CardView) findViewById(R.id.cd);
        img = (ImageView) findViewById(R.id.imageView2);
        tv = (TextView) findViewById(R.id.textView);
        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signup);
        forgot = (Button) findViewById(R.id.forgot);
        facebook = (ImageButton) findViewById(R.id.facebook);
        twitter = (ImageButton) findViewById(R.id.twitter);
        linkedin = (ImageButton) findViewById(R.id.linkedin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        downToUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.downtoup);
        upToDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.uptodown);
//        cd.setVisibility(View.VISIBLE);
        cd.startAnimation(downToUp);
        img.startAnimation(upToDown);
        tv.startAnimation(upToDown);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Username or Password can't be empty",Toast.LENGTH_SHORT).show();;
                }
                else if(password.getText().toString().length()<8){
                    Toast.makeText(getApplicationContext(),"Password is too short.\nMinimum 8 digit required",Toast.LENGTH_SHORT).show();;
                }
                else{
                    Toast.makeText(getApplicationContext(),"Just for UI",Toast.LENGTH_SHORT).show();;
                }

            }
        });

        justForUI(forgot);

        justForUI(signUp);

        justChecking(facebook);

        justChecking(twitter);

        justChecking(linkedin);

    }

    void justForUI(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Just for UI",Toast.LENGTH_SHORT).show();;
            }
        });
    }

    void justChecking(ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Just Checking",Toast.LENGTH_SHORT).show();;
            }
        });
    }

}