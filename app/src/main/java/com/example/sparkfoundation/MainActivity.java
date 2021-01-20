package com.example.sparkfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {
    //Initialize
    CardView cd;
    ImageView img;
    TextView tv;
    Animation downToUp,upToDown;
    Button login, signUp, forgot;
    ImageButton facebook, twitter, linkedIn, google;
    EditText username,password;
    //Initialize for Google
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    //Initialize for Facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cd = findViewById(R.id.cd);
        img = findViewById(R.id.imageView2);
        tv = findViewById(R.id.textView);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signup);
        forgot = findViewById(R.id.forgot);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        linkedIn = findViewById(R.id.linkedin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
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
                    Toast.makeText(getApplicationContext(),"Username or Password can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().length()<8){
                    Toast.makeText(getApplicationContext(),"Password is too short.\nMinimum 8 digit required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Just for UI",Toast.LENGTH_SHORT).show();
                }

            }
        });

        getIntoGoogle(google);

        justForUI(forgot);

        justForUI(signUp);

        justChecking(facebook);

        justChecking(twitter);

        justChecking(linkedIn);

    }

    //For Google Sign In
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            gotoNext();
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail()+"///"+ currentUser.getDisplayName());
        }
    }

    void getIntoGoogle(ImageButton button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInToGoogle();
            }
        });
        configureGoogleClient();
    }

    private void configureGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signInToGoogle() {
        googleSignInClient.signOut();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"SignIn Successful",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithCredential:success");
                            gotoNext();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void gotoNext(){
        Intent in=new Intent(getApplicationContext(), Status_activity.class);
        startActivity(in);
        finish();
    }

    //For Facebook Login
    void getIntoFacebook(ImageButton button) {
        // Initialize Facebook Login button
        Call
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = mBinding.buttonFacebookLogin;
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }





    void justForUI(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Just for UI",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void justChecking(ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Just Checking",Toast.LENGTH_SHORT).show();
            }
        });
    }
}