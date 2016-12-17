package nkosi.roger.manutdcom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * A login screen that offers login via email/password/facebook/twitter/google.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private static String accessToken;

    private static final int RC_SIGN_IN = 9001;

    //use the complete qualified nameso to make sure there are no clashes with other third party login APIs such as twitter and Google
    private com.facebook.login.widget.LoginButton loginButton;
    private com.facebook.CallbackManager callbackManager;

    private TextView welcome;
    private Typeface typeface;

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        if (checkFBLogin() == null){
            Toast.makeText(LoginActivity.this, "Not logged in via facebook", Toast.LENGTH_LONG).show();
            this.loginButton = (com.facebook.login.widget.LoginButton)findViewById(R.id.login_button);
            this.loginButton.setReadPermissions("email");

            this.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    LoginActivity.accessToken = loginResult.getAccessToken().toString();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else if(checkFBLogin() != null){
            startActivity(new Intent(this, Home.class));
            Toast.makeText(LoginActivity.this, "Already logged in Via Facebook", Toast.LENGTH_SHORT).show();
        }

        if (checkGoogleLogin() == null){
            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            this.mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }else if (checkFBLogin() != null){
            startActivity(new Intent(this, Home.class));

        }



        this.welcome = (TextView)findViewById(R.id.welcome);
        this.typeface = Typeface.createFromAsset(getAssets(), "fonts/sebastiana.otf");
        this.welcome.setTypeface(typeface);
        this.welcome.setTextSize(60);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Home.class));
            }
        });
    }

    public String checkFBLogin(){
        String s = null;
        try {
            s = AccessToken.getCurrentAccessToken().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return s;
        }

    }

    public String checkGoogleLogin(){
        String userID = null;
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("man_ud_com_google_signin", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("googlePersonID", "");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Please Login with your google account", Toast.LENGTH_LONG).show();
        } finally {
            return userID;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            default:
                return;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personId = acct.getId();

            SharedPreferences sp = getSharedPreferences("man_ud_com_google_signin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("googlePersonID", personId);
            editor.commit();

            startActivity(new Intent(LoginActivity.this, Home.class));
            finish();

        } else {
            // Signed out, show unauthenticated UI.

        }
    }


}

