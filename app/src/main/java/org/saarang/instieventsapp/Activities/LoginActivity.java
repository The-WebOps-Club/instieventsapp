package org.saarang.instieventsapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.HttpRequest;

/**
 * Created by Ajmal on 08-08-2015.
 */

public class LoginActivity extends Activity {


    private static String LOG_TAG = "LDAPLoginActivity";
    Button btLogin;
    TextView tvLogin;
    EditText etUsername, etPassword;
    TextInputLayout tilUsername;
    String username, password;
    ProgressDialog pDialog;
    Login logintask;
    UserProfile profile;
    Context context = LoginActivity.this;
    LinearLayout llSnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_ldap3);

      /*  Intent intent = new Intent(this, IE_RegistrationIntentService.class);
        startService(intent);
*/
        llSnack = (LinearLayout) findViewById(R.id.llLoginMain);
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        });

        etUsername = (EditText) findViewById(R.id.etUsername);
        etUsername.setHintTextColor(getResources().getColor(R.color.events_secondary_text));

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setHintTextColor(getResources().getColor(R.color.events_secondary_text));
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(LOG_TAG, "Login pressed");
                    processLogin();
                }
                return false;
            }
        });



        //tilUsername = (TextInputLayout) findViewById(R.id.tilUsername);
    }

    private void processLogin() {
        //Getting Text From Edit Texts
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        //Validating roll number format
        if (username.length() >= 8) {
            // tilUsername.setError(null);
            //Checking for connection


//
//
//            pDialog = new ProgressDialog(LoginActivity.this);
//            pDialog.setMessage("Connecting ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();



            if (Connectivity.isNetworkAvailable(context)) {
                logintask = new Login();
                logintask.execute(username, password);
            } else {
                UIUtils.showSnackBar(llSnack, getResources().getString(R.string.error_connection));
            }
        } else {
            // tilUsername.setError("Invalid roll number");
            UIUtils.showSnackBar(llSnack, getResources().getString(R.string.invalid_password));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * AsyncTask for Logging in .
     */
    private class Login extends AsyncTask<String, Void, Void> {

        int status = 400;
        JSONObject jEvent;
        Club club;
        Gson gson = new Gson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... param) {

            // String urlString = URLConstants.SERVER + URLConstants.URL_LOGIN;
            String urlString = URLConstants.URL_LOGIN;
            //Adding Parameters
            JSONObject JSONrequest = new JSONObject();
            try {
                JSONrequest.put("rollNumber", param[0]);
                JSONrequest.put("password", param[1]);
                JSONrequest.put("name", "ajmal");
                JSONrequest.put("hostel", "Narmada");
                Log.d(LOG_TAG, "2 JSONrequest\n" + JSONrequest.toString());
                Log.d(LOG_TAG, "3 urlstring :: " + urlString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Making request

            JSONObject responseJSON = HttpRequest.execute("POST", urlString, null, JSONrequest);
            Log.d(LOG_TAG, "4 responseJSON " + responseJSON.toString());
            if (responseJSON == null) {
                return null;

            }

            try {
                status = responseJSON.getInt("status");
                if (status == 200) {
                    Log.d(LOG_TAG, "5 successfull\n");

                    //Saving INSTIProfile of logged in user
                    profile.saveUser(LoginActivity.this, responseJSON.getJSONObject("data"));
                    JSONArray jEvents = responseJSON.getJSONObject("data").getJSONArray("clubs");
                    for (int i = 0; i < jEvents.length(); i++) {
                        jEvent = jEvents.getJSONObject(i);
                        club = gson.fromJson(jEvent.toString(), Club.class);
                        Log.d(LOG_TAG, jEvent.getString("name"));
                        DatabaseHelper data = new DatabaseHelper(context);
                        data.addClub(club.getCV());
                    }
                } else {
                    Log.d(LOG_TAG, "5 unsuccessfull!! ");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            switch (status) {
                case 200:
                    UserProfile.setUserState(context, 2);
                    Intent intent = new Intent(LoginActivity.this, ClubSubscriptionActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 401:
                    UIUtils.showSnackBar(llSnack, "Invalid credentials, please try again");
                    break;
                default:
                    UIUtils.showSnackBar(llSnack, "There was an error connecting to our server. Please try again");
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}