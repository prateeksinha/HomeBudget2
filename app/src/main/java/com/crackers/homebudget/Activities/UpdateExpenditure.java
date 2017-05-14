package com.crackers.homebudget.Activities;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Expenses;

import java.util.Calendar;

/**
 * Created by Prateek on 24-03-2017.
 */
public class UpdateExpenditure extends Activity implements View.OnClickListener {

    TextView tvUpdateExpenses,tvCancelUpdate,tvExpensesDate;
    EditText etExpensesCategory,etExpensesAmount;
    ImageButton ibDatePicker;
    DBHelper db=null;
    Calendar calendar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayAdapter<ArrayAdapter<Expenses>> expensesArrayList;
    int years,day,month,ex_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_expenditure);

        expensesArrayList=new ArrayAdapter<>(UpdateExpenditure.this,R.layout.expenses_list);

        etExpensesAmount= (EditText) findViewById(R.id.etExpensesAmount);
        etExpensesCategory= (EditText) findViewById(R.id.etExpensesCategory);

        tvExpensesDate= (TextView) findViewById(R.id.tvExpensesDate);
        tvCancelUpdate= (TextView) findViewById(R.id.tvCancelUpdate);
        tvUpdateExpenses= (TextView) findViewById(R.id.tvUpdateExpenses);

        ibDatePicker= (ImageButton) findViewById(R.id.ibDatePicker);
        calendar= Calendar.getInstance();

        sharedPreferences=getSharedPreferences("prefName",MODE_APPEND);
        String category = sharedPreferences.getString("Category", null);
        String amount = sharedPreferences.getString("Amount", null);
        String date = sharedPreferences.getString("Date", null);
        ex_id = sharedPreferences.getInt("Id", 0);

        etExpensesCategory.setText(category);
        etExpensesAmount.setText(amount);
        tvExpensesDate.setText(date);

        db=new DBHelper(getApplicationContext());
        eventHandling();
    }

    private void eventHandling() {
        tvUpdateExpenses.setOnClickListener(this);
        ibDatePicker.setOnClickListener(this);
        tvCancelUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvUpdateExpenses:
                int id3=ex_id;
                String category=etExpensesCategory.getText().toString();
                String a=etExpensesAmount.getText().toString();
                Double amount=Double.parseDouble(a);
                String date=tvExpensesDate.getText().toString();
                if (category.isEmpty() || date.isEmpty() || amount==null) {
                    Toast.makeText(getApplicationContext(), "Fill The Data", Toast.LENGTH_SHORT).show();
                }
                else{
                    long id=db.updateRecExp(id3,category,amount,date);
                    if(id!=-1){
                        Intent i = new Intent(getApplication(), ExpensesList.class);
                        startActivity(i);

                        Toast.makeText(getApplicationContext(), "Record Successfully updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            case R.id.ibDatePicker:
                DatePickerDialog dpd = new DatePickerDialog(UpdateExpenditure.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        years = year;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                        tvExpensesDate.setText(day + "-" + month + "-" + years);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;

            case R.id.tvCancelUpdate:
                Intent i=new Intent(getApplicationContext(),ExpensesList.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
