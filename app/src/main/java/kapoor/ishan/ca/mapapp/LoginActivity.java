package kapoor.ishan.ca.mapapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private Button signInButton;
    private Button RegisterButton;
    private EditText passwordEditText;
    private EditText emailEditText;
    private LinearLayout loginForm;
    private ProgressBar progressBar;
    public static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;



    public static final String FIELDS_KEY = "fields";
    public static final String FIELDS = "first_name, last_name, email, id";

    CallbackManager callbackManager;
    TextView nameView;
    TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.login_progress);
        loginForm = (LinearLayout)findViewById(R.id.email_login_form);
        callbackManager = CallbackManager.Factory.create();
        nameView = (TextView) findViewById(R.id.name_tv);
        emailView = (TextView) findViewById(R.id.email_tv);
        signInButton = (Button)findViewById(R.id.sign_in_button);
        RegisterButton = (Button) findViewById(R.id.register_button);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);



        RegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signInUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });









    }

    private void displayUserInfo(JSONObject object) {
        String first_name, last_name, email, ID;
        first_name ="";
        last_name = "";
        email = "";
        ID = "";

        try {
            Log.d(TAG, "jsonTrySuccesful");
            first_name = object.getString("first_name");
            last_name  = object.getString("last_name");
            email = object.getString("email");
            ID = object.getString("id");

        } catch (JSONException e) {
            Log.d(TAG, "jsonExceptionCaught");

            e.printStackTrace();
        }
        nameView.setText(first_name +  " " + last_name);
        emailView.setText(email);
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);

    }

    public void updateUI(FirebaseUser user){
        if (user == null){

        }
        else {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }



    public void registerUser(String email, String password){
        if (!email.contains("@")){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<4){
            Toast.makeText(this, "Please Enter a longer password", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideLoading();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            hideLoading();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });


    }

    public void signInUser(String email, String password){

        if (email!=null&& password!=null && email.length()!=0 && password.length()!=0) {
            showLoading();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideLoading();
                            if (task.isSuccessful()) {
                                hideLoading();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                hideLoading();
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
        else {
            Toast.makeText(LoginActivity.this, "Please Enter an email and a password",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void showLoading(){
        loginForm.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){

        progressBar.setVisibility(View.GONE);
        loginForm.setVisibility(View.VISIBLE);
    }

    public void signOutUser(){
        if (firebaseAuth!=null){
            if (firebaseAuth.getCurrentUser()!=null){
                String user = firebaseAuth.getCurrentUser().getEmail();
                firebaseAuth.signOut();
                Toast.makeText(this, user + " has been signed out", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

