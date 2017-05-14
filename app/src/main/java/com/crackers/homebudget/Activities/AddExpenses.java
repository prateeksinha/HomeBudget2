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
 * Created by Prateek on 24-07-2016.
 */
public class AddExpenses extends Activity implements View.OnClickListener {

    DBHelper dbHelper=null;
    EditText etExpensesAmount,etExpensesCategory;
    TextView tvDate,tvSave,tvCancel;
    Button bShowList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_add);

        sharedPreferences=getSharedPreferences("prefName",MODE_APPEND);
        editor=sharedPreferences.edit();



        initializeContent();
        String date= new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        tvDate.setText(date);
        UserName=sharedPreferences.getString("userName",null);

        eventHandling();
    }

    private void eventHandling() {
        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        bShowList.setOnClickListener(this);
    }

    private void initializeContent() {

        dbHelper              = new DBHelper(getApplicationContext());

        etExpensesAmount      = (EditText) findViewById(R.id.etExpensesAmount);
        etExpensesCategory    = (EditText) findViewById(R.id.etExpensesCategory);

        tvDate                = (TextView) findViewById(R.id.tvDate);
        tvSave                = (TextView) findViewById(R.id.tvSave);
        tvCancel              = (TextView) findViewById(R.id.tvCancel);
        bShowList             = (Button) findViewById(R.id.bShowList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tvSave:

                String m=etExpensesAmount.getText().toString();
                Double Amount=Double.parseDouble(m);
                String Category=etExpensesCategory.getText().toString();
                String Date=tvDate.getText().toString();

                if(Amount==null||Category.isEmpty()) {
                    Toast.makeText(getApplication(),"Fill The Data",Toast.LENGTH_SHORT).show();
                }
                else{
                        long id = dbHelper.insertExpensesRec(UserName,Category, Amount, Date);
                        if (id != -1) {
                            Toast.makeText(getApplicationContext(), "Expenses Added Successfully", Toast.LENGTH_SHORT).show();
                            etExpensesCategory.getText().clear();
                            etExpensesAmount.getText().clear();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Data not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                break;

            case R.id.tvCancel:
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.bShowList:
                i=new Intent(getApplicationContext(),ExpensesList.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
