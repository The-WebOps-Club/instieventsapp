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
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;

/**
 * Created by Ajmal on 08-08-2015.
 */

public class LoginActivity extends Activity {


    private static String LOG_TAG = "LDAPLoginActivity";
    private static String postUsername = "roll";
    private static String postPassword = "pass";

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


            if (Connectivity.isNetworkAvailable(context)) {
                logintask = new Login();
                logintask.execute(username, password);
            } else {
                UIUtils.showSnackBar(llSnack, getResources().getString(R.string.error_connection));
            }
        } else {
            UIUtils.showSnackBar(llSnack, getResources().getString(R.string.invalid_credentials));
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


            //Adding params for requesting insti server
            ArrayList<PostParam> instiPostParams = new ArrayList<PostParam>();
            PostParam postUser = new PostParam(postUsername, param[0]);
            PostParam postPass = new PostParam(postPassword, param[1]);
            instiPostParams.add(postUser);
            instiPostParams.add(postPass);

            //Making request to Insti Server

            JSONObject instiResponseJSON = PostRequest.execute(URLConstants.URL_INSTI_LOGIN, instiPostParams, null);
            Log.d(LOG_TAG, "4 instiResponseJSON " + instiResponseJSON.toString());


            //Checking response from Insti server

            //Response from server due to invalid credentials has length <40 right now
            if (instiResponseJSON.toString().length() < 40) {
                status = 666;
                return null;
            }

            //Storing userProfile of logged in user into class UserJSON
            profile = new UserProfile(instiResponseJSON);

            Log.d(LOG_TAG, profile.getFullname() + "  " + profile.getId()  + "  " + profile.getHostel() + "  " + profile.getUsername());
            //Adding Parameters for saarang server
            JSONObject JSONrequestSaarang = new JSONObject();
            try {
                JSONrequestSaarang.put("rollNumber", param[0]);
                JSONrequestSaarang.put("password", "password");
                JSONrequestSaarang.put("name", profile.getFullname());
                JSONrequestSaarang.put("hostel", profile.getHostel());
                Log.d(LOG_TAG, "4 JSONrequestSaarang\n" + JSONrequestSaarang.toString());
                Log.d(LOG_TAG, "5 urlstringSaarang :: " + URLConstants.URL_LOGIN);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Making request to Saarang server
            JSONObject saarangResponseJSON = HttpRequest.execute("POST", URLConstants.URL_LOGIN, null, JSONrequestSaarang);
            Log.d(LOG_TAG, "6 saarangResponseJSON " + saarangResponseJSON.toString());
            if (saarangResponseJSON == null) {
                return null;
            }

            try {
                status = saarangResponseJSON.getInt("status");
                if (status == 200) {
                    Log.d(LOG_TAG, "7 successfull\n");

                    //Saving INSTIProfile of logged in user
                    profile.saveUser(LoginActivity.this, saarangResponseJSON.getJSONObject("data"));

                    JSONArray jEvents = saarangResponseJSON.getJSONObject("data").getJSONArray("clubs");
                    for (int i = 0; i < jEvents.length(); i++) {
                        jEvent = jEvents.getJSONObject(i);
                        club = gson.fromJson(jEvent.toString(), Club.class);
                        Log.d(LOG_TAG, jEvent.getString("name"));
                        DatabaseHelper data = new DatabaseHelper(context);
                        data.addClub(club.getCV());
                    }
                } else {
                    Log.d(LOG_TAG, "7 unsuccessfull!! ");
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
                case 666:
                    UIUtils.showSnackBar(llSnack, "Invalid Credentials,please try again");
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