package com.bdjobs.training.loginsharedpreftest;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginAcitvity extends AppCompatActivity {

    EditText userNameET, passwordET;
    Button loginBTN;
    String userName, password;
    SessionManagement session;
    String baseUrl = "http://my.bdjobs.com/apps/mybdjobs/apps_agent_log.asp";
    static final String TAG = "Login";

    /*username = mybdjobs
    password = mybdjobs12*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);
        initializer();
        onClickListeners();
        session = new SessionManagement(getApplicationContext());

        boolean checkin = session.isLoggedIn();
        if (checkin == true) {

            Intent intent = new Intent(getApplicationContext(), SecondAcitivity.class);
            startActivity(intent);
        }


    }

    private void initializer() {
        userNameET = (EditText) findViewById(R.id.userNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginBTN = (Button) findViewById(R.id.loginBTN);

    }


    private void onClickListeners() {
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameET.getText().toString();
                password = passwordET.getText().toString();

                if (userName == null || userName.isEmpty() || userName.equalsIgnoreCase(null)) {
                    Toast.makeText(LoginAcitvity.this, "Username can not be empty!", Toast.LENGTH_SHORT).show();
                } else if (password == null || password.isEmpty() || password.equalsIgnoreCase(null)) {
                    Toast.makeText(LoginAcitvity.this, "Password can not be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    doLogin(userName, password);

                }


            }
        });


    }

    private void doLogin(final String userName1, final String password1) {

        Log.d(TAG, "userName: " + userName1 + "\nPassword: " + password1);

        final StringRequest loginRequest1 = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject loginObject = new JSONObject(response);

                    String message = loginObject.getString("Message");
                    String messageType = loginObject.getString("messageType");
                    String isCvPosted = loginObject.getString("isCvPosted");
                    String name = loginObject.getString("name");
                    String email = loginObject.getString("email");
                    String userId = loginObject.getString("userId");
                    String decodId = loginObject.getString("decodId");
                  //  String userName = loginObject.getString("userName");
                    String AppsDate = loginObject.getString("AppsDate");
                    String age = loginObject.getString("age");

                    String exp1 = loginObject.getString("exp");
                    String catagoryId = loginObject.getString("catagoryId");
                    String gender = loginObject.getString("gender");
                    String resumeUpdateON = loginObject.getString("resumeUpdateON");
                    String IsResumeUpdate = loginObject.getString("IsResumeUpdate");
                    String userPicUrl = loginObject.getString("userPicUrl");

                    session.savedData(response);
                    String messagetype = loginObject.getString("messageType");
                    Toast.makeText(LoginAcitvity.this, email, Toast.LENGTH_SHORT).show();

                    if (messagetype.equals("1")) {

                        //   session.createLoginSession(userName1, password1);
                        session.setUserName(userName1);
                        session.setPassword(password1);

                        Intent intent = new Intent(getApplicationContext(), SecondAcitivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginAcitvity.this, "Wrong Entry", Toast.LENGTH_SHORT).show();
                    }
                    //                   Log.d(TAG, "Message: " + message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName1);
                params.put("password", password1);
                return params;
            }

        };


        MySingleton.getInstance(this).addToRequestQueue(loginRequest1);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}
