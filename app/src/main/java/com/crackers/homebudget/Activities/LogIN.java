package com.crackers.homebudget.Activities;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crackers.homebudget.R;

/**
 * Created by Prateek on 27-03-2017.
 */
public class LogIN extends Activity implements View.OnClickListener {
    TextView tvLogIn,tvSignUp,tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initializeContent();
        eventHandling();
    }

    private void eventHandling() {
        tvSignUp.setOnClickListener(this);
        tvLogIn.setOnClickListener(this);
        tvInfo.setOnClickListener(this);
    }

    private void initializeContent() {
        tvInfo= (TextView) findViewById(R.id.tvInfo);
        tvLogIn= (TextView) findViewById(R.id.tvLogIn);
        tvSignUp= (TextView) findViewById(R.id.tvSignUp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSignUp:
                Intent si=new Intent(getApplicationContext(),SignUp.class);
                startActivity(si);
                finish();
                break;
            case R.id.tvLogIn:
                Intent li=new Intent(getApplicationContext(),SignIn.class);
                startActivity(li);
                finish();
                break;
            case R.id.tvInfo:
                li=new Intent(getApplicationContext(),Information.class);
                startActivity(li);
                break;
        }
    }
}
