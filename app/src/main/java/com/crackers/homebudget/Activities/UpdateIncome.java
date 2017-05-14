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
import com.crackers.homebudget.Utilities.Income;

import java.util.Calendar;
/**
 * Created by Prateek on 19-09-2016.
 */
public class UpdateIncome extends Activity implements View.OnClickListener {

    EditText etIncomeCategory, etIncomeAmount;
    TextView tvUpdateIncome, tvCancelUpdate, tvIncomeDate;
    ImageButton ibDatePicker;
    DBHelper dbHelper = null;
    Calendar calendar;

    SharedPreferences sharedPreferences;
    ArrayAdapter<ArrayAdapter<Income>> incomeArrayList;
    int years, month, day, id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_income);

        incomeArrayList = new ArrayAdapter<>(UpdateIncome.this, R.layout.income_list);

        etIncomeCategory = (EditText) findViewById(R.id.etIncomeCategory);
        etIncomeAmount = (EditText) findViewById(R.id.etIncomeAmount);

        ibDatePicker = (ImageButton) findViewById(R.id.ibDatePicker);

        tvIncomeDate = (TextView) findViewById(R.id.tvIncomeDate);
        calendar = Calendar.getInstance();
        tvUpdateIncome = (TextView) findViewById(R.id.tvUpdateIncome);
        tvCancelUpdate = (TextView) findViewById(R.id.tvCancelUpdate);

        sharedPreferences = getSharedPreferences("prefName", MODE_APPEND);
        String category = sharedPreferences.getString("Category", null);
        String amount = sharedPreferences.getString("Amount", null);
        String date = sharedPreferences.getString("Date", null);
        id1 = sharedPreferences.getInt("Id", 0);


        etIncomeCategory.setText(category);
        etIncomeAmount.setText(amount);
        tvIncomeDate.setText(date);

        dbHelper = new DBHelper(getApplicationContext());
        eventHandling();
    }

    private void eventHandling() {
        tvUpdateIncome.setOnClickListener(this);
        tvCancelUpdate.setOnClickListener(this);
        ibDatePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvUpdateIncome:
                int id2 = id1;
                String Category = etIncomeCategory.getText().toString();
                String  b= etIncomeAmount.getText().toString();
                Double Amount=Double.parseDouble(b);
                String Date = tvIncomeDate.getText().toString();

                if (Category.isEmpty() || Date.isEmpty() || Amount==null) {
                    Toast.makeText(getApplicationContext(), "Fill The Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    long id = dbHelper.updateRecInc(id2, Date, Amount, Category);
                    if (id != -1) {
                        Intent i = new Intent(getApplication(), IncomeList.class);
                        startActivity(i);

                        Toast.makeText(getApplicationContext(), "Record Successfully updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            case R.id.ibDatePicker:
                DatePickerDialog dpd = new DatePickerDialog(UpdateIncome.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        years = year;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                        tvIncomeDate.setText(day + "-" + month + "-" + years);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;

            case R.id.tvCancelUpdate:
                Intent i=new Intent(getApplicationContext(),IncomeList.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
