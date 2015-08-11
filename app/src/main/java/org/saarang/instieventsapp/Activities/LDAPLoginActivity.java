package org.saarang.instieventsapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.saarang.instieventsapp.R;

/**
 * Created by Ajmal on 08-08-2015.
 */

public class LDAPLoginActivity extends Activity{
    Button btLogin;
    TextView tvLogin;
    EditText etUsername, etPassword;
    TextInputLayout tilUsername, tilPassword;
    String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_ldap);
        /*
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        });
        */
    }
    private void processLogin(){
        //Getting Text From Edit Texts
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        //Validating Username
        /*
        if(true){

        }
        else{
            tilUsername.setError("Invalid LDAP Username");
        }
*/
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
}