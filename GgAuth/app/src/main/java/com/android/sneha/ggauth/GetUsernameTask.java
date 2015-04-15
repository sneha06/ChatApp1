package com.android.sneha.ggauth;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sneha on 21/1/15.
 */
public class GetUsernameTask extends AsyncTask {

    MainActivity as;
    Activity mActivity;
    String mScope;
    String mEmail;

    GetUsernameTask(Activity activity, String name, String scope) {
        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = name;
    }

    private static final String TAG = "TokenInfoTask";
    private static final String NAME_KEY = "given_name";

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            String token = fetchToken();

            if (token != null) {
                // Insert the good stuff here.
                // Use the token to access the user's Google data.
                as.tv.setText(token);
                fetchNameFromProfileServer();
            }
        } catch (IOException e) {
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (final Exception e) {
            // GooglePlayServices.apk is either old, disabled, or not present
            // so we need to show the user some UI in the activity to recover.
            //mActivity.handleException(e);
        }
//        } catch (GoogleAuthException fatalException) {
//            // Some other type of unrecoverable exception has occurred.
//            // Report and log the error as appropriate for your app.
//
//        }
        return null;
    }


    private void fetchNameFromProfileServer() throws IOException, JSONException {
        String token = fetchToken();
        if (token == null) {
            // error has already been handled in fetchToken()
            return;
        }
        URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int sc = con.getResponseCode();
        if (sc == 200) {
            InputStream is = con.getInputStream();
            String name = getFirstName(readResponse(is));
            as.show("Hello " + name + "!");
            is.close();
            return;
        } else if (sc == 401) {
            try{
                GoogleAuthUtil.clearToken(mActivity, token);
            } catch (Exception e) {
                onError("Server auth error, please try again.", e);
            }
            onError("Server auth error, please try again.", null);
            Log.i(TAG, "Server auth error: " + readResponse(con.getErrorStream()));
            return;
        } else {
            onError("Server returned the following error code: " + sc, null);
            return;
        }
    }
    protected void onError(final String msg, Exception e) {
        if (e != null) {
            Log.e(TAG, "Exception: ", e);
        }
        as.show(msg);  // will be run in UI thread
    }
    private String getFirstName(String jsonResponse) throws JSONException {
        JSONObject profile = new JSONObject(jsonResponse);
        return profile.getString(NAME_KEY);
    }

    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = is.read(data, 0, data.length)) >= 0) {
            bos.write(data, 0, len);
        }
        return new String(bos.toByteArray(), "UTF-8");
    }

}
