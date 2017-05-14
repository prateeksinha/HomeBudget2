package com.crackers.homebudget.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;

/**
 * Created by Prateek on 28-03-2017.
 */
public class SignIn extends Activity implements View.OnClickListener {
    EditText etEmail,etPasswordL;
    TextView tvSignIn,tvSignInCancel;
    DBHelper dbHelper=null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        sharedPreferences=getSharedPreferences("prefName",MODE_APPEND);
        editor=sharedPreferences.edit();

        dbHelper=new DBHelper(getApplicationContext());
        initializeContent();
        eventHandling();
    }

    private void eventHandling() {
        tvSignIn.setOnClickListener(this);
        tvSignInCancel.setOnClickListener(this);
    }

    private void initializeContent() {

        tvSignIn= (TextView) findViewById(R.id.tvSignIn);
        tvSignInCancel= (TextView) findViewById(R.id.tvSignInCancel);

        etEmail= (EditText) findViewById(R.id.etEmail);
        etPasswordL= (EditText) findViewById(R.id.etPasswordL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSignIn:
                String email=etEmail.getText().toString();
                String password=etPasswordL.getText().toString();
                editor.putString("userName",email);
                editor.commit();
                boolean val=dbHelper.logIn(email,password);
                if(val){
                    Intent li=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(li);
                    Toast.makeText(getApplicationContext(),"Log In Successful",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sign up first",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvSignInCancel:
                Intent i=new Intent(getApplicationContext(),LogIN.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
