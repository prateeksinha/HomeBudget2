package com.crackers.homebudget.Activities;

import android.app.Activity;
import android.content.Intent;
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
public class SignUp extends Activity implements View.OnClickListener {
    TextView tvSignUpSave,tvSignUpCancel;
    EditText etEmailId,etUserName,etPasswordA,etPasswordB;
    DBHelper dbHelper=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        dbHelper=new DBHelper(getApplicationContext());
        initializeContent();
        eventHandling();
    }

    private void eventHandling() {
        tvSignUpSave.setOnClickListener(this);
        tvSignUpCancel.setOnClickListener(this);
    }

    private void initializeContent() {
        tvSignUpSave    = (TextView) findViewById(R.id.tvSignUpSave);
        tvSignUpCancel  = (TextView) findViewById(R.id.tvSignUpCancel);

        etEmailId   = (EditText) findViewById(R.id.etEmailId);
        etUserName  = (EditText) findViewById(R.id.etUserName);
        etPasswordA = (EditText) findViewById(R.id.etPasswordA);
        etPasswordB = (EditText) findViewById(R.id.etPasswordB);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tvSignUpSave:
                String EmailId=etEmailId.getText().toString();
                String username=etUserName.getText().toString();
                String passA=etPasswordA.getText().toString();
                String passB=etPasswordB.getText().toString();
                if(EmailId.isEmpty()||username.isEmpty()||passA.isEmpty()||passB.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the Data", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (passA.equals(passB)) {
                        long id = dbHelper.signUpRec(EmailId ,passA ,username);
                        if (id != -1) {
                            Intent si = new Intent(getApplicationContext(), LogIN.class);
                            startActivity(si);
                            Toast.makeText(getApplicationContext(),"Thank you For Registering",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "EmailID Already Exist Please try with different EmailID", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
                break;
            case R.id.tvSignUpCancel:
                Intent i=new Intent(getApplicationContext(),LogIN.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
