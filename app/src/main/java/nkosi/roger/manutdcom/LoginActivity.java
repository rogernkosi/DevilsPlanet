package nkosi.roger.manutdcom;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;

/**
 * A login screen that offers login via email/password/facebook/twitter/google.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private static String accessToken;

    //use the complete qualified nameso to make sure there are no clashes with other third party login APIs such as twitter and Google
    private com.facebook.login.widget.LoginButton loginButton;
    private com.facebook.CallbackManager callbackManager;

    private TextView welcome;
    private Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        if (checkFBLogin().isEmpty()){
            Toast.makeText(LoginActivity.this, "Not logged in via facebook", Toast.LENGTH_SHORT).show();
        }

        this.loginButton = (com.facebook.login.widget.LoginButton)findViewById(R.id.login_button);
        this.loginButton.setReadPermissions("email");

        this.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginActivity.accessToken =  loginResult.getAccessToken().toString();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        this.welcome = (TextView)findViewById(R.id.welcome);
        this.typeface = Typeface.createFromAsset(getAssets(), "fonts/sebastiana.otf");
        this.welcome.setTypeface(typeface);
        this.welcome.setTextSize(60);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Home.class));
            }
        });

    }

    public String checkFBLogin(){
        return AccessToken.getCurrentAccessToken().toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

