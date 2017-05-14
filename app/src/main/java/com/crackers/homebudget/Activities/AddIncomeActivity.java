package com.crackers.homebudget.Activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Prateek on 31-07-2016.
 */
public class AddIncomeActivity extends Activity implements View.OnClickListener {

    DBHelper dbHelper=null;
    EditText etAddIncomeCategory,etAddIncomeAmount;
    TextView tvDate,tvSave,tvCancel;
    Button bShowList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String UserName;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_income);

            sharedPreferences = getSharedPreferences("prefName", MODE_APPEND);
            editor = sharedPreferences.edit();

            UserName = sharedPreferences.getString("userName", null);
            String d = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            initializeContentView();

            tvDate.setText(d);

            eventHandling();
        }
    private void eventHandling() {

        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        bShowList.setOnClickListener(this);
    }

    private void initializeContentView() {
        dbHelper = new DBHelper(getApplicationContext());
        etAddIncomeAmount = (EditText) findViewById(R.id.etAddIncomeAmount);
        etAddIncomeCategory = (EditText) findViewById(R.id.etAddIncomeCategory);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        bShowList = (Button) findViewById(R.id.bShowList);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvSave:

                String n = etAddIncomeAmount.getText().toString();
                Double Amount=Double.parseDouble(n);
                String Category = etAddIncomeCategory.getText().toString();
                String Date = tvDate.getText().toString();

                if (Category.isEmpty() || Amount==null) {
                    Toast.makeText(getApplicationContext(), "Fill The Data", Toast.LENGTH_SHORT).show();
                } else {
                    long id = dbHelper.insertIncomeRec(UserName, Category, Amount, Date);
                    if (id != -1) {
                        Toast.makeText(getApplicationContext(), "Income Added Successfully", Toast.LENGTH_SHORT).show();
                        etAddIncomeCategory.getText().clear();
                        etAddIncomeAmount.getText().clear();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "unable to save data", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tvCancel:
                Intent c = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(c);
                finish();
                break;
            case R.id.bShowList:
                Intent i=new Intent(getApplicationContext(),IncomeList.class);
                startActivity(i);
                finish();
                break;
        }
    }
}

