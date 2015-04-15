package com.android.sneha.ggauth;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;


public class MainActivity extends ActionBarActivity {

    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    String mEmail;
    private static final String SCOPE =
            "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;

    static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    SignInButton gButton;
    TextView tv;

    private static final String TAG = "PlayHelloActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gButton = (SignInButton) findViewById(R.id.sign_in_button);
        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsername();
            }
        });

        tv = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

        if(isGooglePlayServicesAvailable(MainActivity.this) == ConnectionResult.SUCCESS){

            //Toast.makeText(this, "Google play services is Avialable", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(MainActivity.this,"User Account picked",Toast.LENGTH_SHORT);
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
           // Toast.makeText(MainActivity.this,"User Account picked",Toast.LENGTH_SHORT);

            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth token
                Toast.makeText(MainActivity.this,mEmail,Toast.LENGTH_SHORT);

                getUsername();
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this,"you must pick_account", Toast.LENGTH_SHORT).show();
            }
        } else {
            if ((requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR ||
                    requestCode == REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR)
                    && resultCode == RESULT_OK) {
                handleAuthorizeResult(resultCode, data);
                return;
            }else{
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

        // Later, more code will go here to handle the result from some exceptions...
    }

    private void handleAuthorizeResult(int resultCode, Intent data) {
        if (data == null) {
            show("Unknown error, click the button again");
            return;
        }
        if (resultCode == RESULT_OK) {
            Log.i(TAG, "Retrying");
            new GetUsernameTask(MainActivity.this, mEmail, SCOPE).execute();
            return;
        }
        if (resultCode == RESULT_CANCELED) {
            show("User rejected authorization.");
            return;
        }
        show("Unknown error, click the button again");
    }
    private void getUsername() {
        if (mEmail == null) {
            pickUserAccount();
        } else {
            if (isDeviceOnline()) {
                new GetUsernameTask(MainActivity.this, mEmail, SCOPE).execute();
            } else {
                Toast.makeText(this, "not_online", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    public void handleException(final Exception e) {
        // Because this call comes from the AsyncTask, we must ensure that the following
        // code instead executes on the UI thread.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    // The Google Play services APK is old, disabled, or not present.
                    // Show a dialog created by Google Play services that allows
                    // the user to update the APK
                    int statusCode = ((GooglePlayServicesAvailabilityException)e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                            MainActivity.this,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    // Unable to authenticate, such as when the user has not yet granted
                    // the app access to the account, but the user can fix this.
                    // Forward the user to an activity in Google Play services.
                    Intent intent = ((UserRecoverableAuthException)e).getIntent();
                    startActivityForResult(intent,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }
            }
        });
    }

    //Project ID: kinetic-song-832
    // privatr key password = notasecret
    /*CLIENT ID  = 508309715672-11vpphgnng0lhcdqjveorh7pteo7u872.apps.googleusercontent.com
    EMAIL ADDRESS  =  508309715672-11vpphgnng0lhcdqjveorh7pteo7u872@developer.gserviceaccount.com
    CERTIFICATE FINGERPRINTS = 8da6679e69553c26b8328f4d4c67e673a9a1a5e2*/

    // SHA1: 49:5D:05:DC:D2:DC:1C:79:CA:6E:F7:E3:C2:57:00:32:87:77:FC:45

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    public void show(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(message);
               // Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG);
            }
        });
    }

}
