package com.crackers.homebudget.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.NumberFormatException;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;

import java.security.SignedObject;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    double a,c,e,f;
    TextView tvLogOut,tvShowName,tvAddIncome,tvShowIncome,tvAddExpenses,tvShowExpenses,tvTotalBalanceShow,tvIncomeShow,tvExpensesShow;
    ImageView ibInfo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHelper dbHelper=null;
    SQLiteDatabase sqLiteDatabase=null;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new DBHelper(getApplicationContext());
        sharedPreferences=getSharedPreferences("prefName",MODE_APPEND);
        editor=sharedPreferences.edit();

        initializeContentView();

        eventHandling();


        String userName=sharedPreferences.getString("userName",null);
        topView(userName);
        tvShowName.setText("WEL-COME::\n"+Name);
        frontShowIncome(userName);
        frontShowExpenses(userName);
        frontShowTotal();
    }

    private void topView(String x) {
        String UserName=x;
        sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor2=sqLiteDatabase.rawQuery("Select Name from SignUpTable Where UserName=?",new String[]{UserName});
        if(cursor2.moveToFirst()||cursor2.moveToLast()) {
            Name = cursor2.getString(cursor2.getColumnIndex("Name"));
        }
    }

    void frontShowExpenses(String userName){
        String u=userName;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cur= sqLiteDatabase.rawQuery("Select Amount From BudgetExpensesTBL Where UserName=?", new String[]{u});
        if(cur.moveToFirst()){
                do {
                    String d=cur.getString(cur.getColumnIndex("Amount"));
                    e=Double.parseDouble(d);
                    f+=e;
                }while (cur.moveToNext());
            }
            String exA=String.valueOf(f);
            tvExpensesShow.setText("Total Expenses :: "+exA);
        }

    void frontShowIncome(String x) {
        String userName=x;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select Amount from BudgetIncomeTBL Where UserName=?", new String[]{userName});
        if(cursor.moveToFirst()) {
            do {
                String amount = cursor.getString(cursor.getColumnIndex("Amount"));

                a = Double.parseDouble(amount);
                c += a;
            } while (cursor.moveToNext());

        }
        String inA=String.valueOf(c);
        tvIncomeShow.setText("Total Income ::  "+inA);
    }

    void frontShowTotal() {
        double d=c-f;
        String g=String .valueOf(d);
        tvTotalBalanceShow.setText("Total Balance ::   "+g);
    }

    private void eventHandling() {

        tvAddIncome.setOnClickListener(this);
        tvAddExpenses.setOnClickListener(this);
        tvShowIncome.setOnClickListener(this);
        tvShowExpenses.setOnClickListener(this);
        ibInfo.setOnClickListener(this);
        tvLogOut.setOnClickListener(this);

    }

    private void initializeContentView() {
        ibInfo              = (ImageView) findViewById(R.id.ibInfo);
        tvLogOut            = (TextView) findViewById(R.id.tvLogOut);
        tvShowName          = (TextView) findViewById(R.id.tvShowName);
        tvAddIncome         = (TextView) findViewById(R.id.tvAddIncome);
        tvAddExpenses       = (TextView) findViewById(R.id.tvAddExpenses);
        tvShowIncome        = (TextView) findViewById(R.id.tvShowIncome);
        tvShowExpenses      = (TextView) findViewById(R.id.tvShowExpenses);
        tvTotalBalanceShow  = (TextView) findViewById(R.id.tvTotalBalanceShow);
        tvIncomeShow        = (TextView) findViewById(R.id.tvIncomeShow);
        tvExpensesShow      = (TextView) findViewById(R.id.tvExpensesShow);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvAddIncome:
                Intent i;
                i=new Intent(MainActivity.this,AddIncomeActivity.class);
                startActivity(i);
                break;

            case R.id.tvAddExpenses:
                i=new Intent(MainActivity.this,AddExpenses.class);
                startActivity(i);
                break;

            case R.id.tvShowIncome:
                i=new Intent(MainActivity.this, IncomeList.class);
                startActivity(i);
                break;
            case R.id.tvShowExpenses:
                i=new Intent(MainActivity.this,ExpensesList.class);
                startActivity(i);
                break;
            case R.id.ibInfo:
                i=new Intent(MainActivity.this,HelpApp.class);
                startActivity(i);
                break;
            case R.id.tvLogOut:
                i=new Intent(MainActivity.this,LogIN.class);
                startActivity(i);
                finish();
                break;
            case R.id.default_activity_button:
                finish();
                break;
        }
    }
}
