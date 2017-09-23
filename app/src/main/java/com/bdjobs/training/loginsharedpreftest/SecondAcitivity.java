package com.bdjobs.training.loginsharedpreftest;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondAcitivity extends AppCompatActivity {
    Button logout;
    TextView showTV;
    SessionManagement managementSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_acitivity);
        logout = (Button) findViewById(R.id.logoutBTN);
        showTV = (TextView) findViewById(R.id.msgTV);
        managementSecond = new SessionManagement(getApplicationContext());
        showTV.setText(managementSecond.getData());
        final SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        Toast.makeText(SecondAcitivity.this, sessionManagement.getData(SessionManagement.KEY_NAME), Toast.LENGTH_SHORT).show();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.logoutUser();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}
