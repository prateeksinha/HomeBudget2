package com.crackers.homebudget.Activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.crackers.homebudget.Adapters.IncomeListAdapter;
import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Income;
import java.util.ArrayList;
import java.util.Date;

public class IncomeList extends Activity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<Income> incomeArrayList;
    ArrayAdapter incomeArrayAdapter;

    ImageButton ibBack;

    DBHelper dbHelper = null;
    SQLiteDatabase sqLiteDatabase = null;
    ListView lvIncome;
    int Id,a,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_list);

        sharedPreferences = getSharedPreferences("prefName", MODE_APPEND);
        editor = sharedPreferences.edit();

        incomeArrayList = new ArrayList<Income>();

        dbHelper = new DBHelper(getApplicationContext());

        ibBack= (ImageButton) findViewById(R.id.ibBack);

        lvIncome = (ListView) findViewById(R.id.lvIncome);
        lvIncome.setAdapter(new ArrayAdapter<Income>(this, R.layout.income_list));
        incomeArrayAdapter = new ArrayAdapter<Income>(getApplicationContext(), R.layout.income_list,R.id.tvCategory,incomeArrayList);

        String userName=sharedPreferences.getString("userName",null);
        getDataFromDB(userName);

        IncomeListAdapter adapter = new IncomeListAdapter(IncomeList.this, incomeArrayList);
        lvIncome.setAdapter(adapter);

        eventHandling();
    }


    private void eventHandling() {

        ibBack.setOnClickListener(this);
        registerForContextMenu(lvIncome);
    }

    private void getDataFromDB(String x) {
        String userName=x;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from BudgetIncomeTBL Where UserName=?", new String[]{userName});
        if (cursor.moveToFirst()) {
            do {
                Income i = new Income();
                i.setId(cursor.getInt(Id));
                i.setCategory(cursor.getString(cursor.getColumnIndex("Category")));
                i.setAmount(cursor.getDouble(cursor.getColumnIndex("Amount")));
                i.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                ((BaseAdapter)lvIncome.getAdapter()).notifyDataSetChanged();
                incomeArrayAdapter.notifyDataSetChanged();
                incomeArrayList.add(i);
            }
            while (cursor.moveToNext());
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0, 1, 0, "Delete");
        menu.add(0, 2, 1, "Edit");
    }

    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == 1) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            final String Category = incomeArrayList.get(info.position).getCategory();
            final String Date = incomeArrayList.get(info.position).getDate();
            final Double Amount = incomeArrayList.get(info.position).getAmount();

            long id = dbHelper.deleteRec(Date, Amount, Category);
            if (id != -1) {
                Intent in = new Intent(getApplicationContext(), IncomeList.class);
                startActivity(in);
                finish();
            }
        }
        else if (item.getItemId() == 2) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                final int list_id=incomeArrayList.get(info.position).getId();
                final String Date = incomeArrayList.get(info.position).getDate();
                final String Category = incomeArrayList.get(info.position).getCategory();
                final Double Amount = incomeArrayList.get(info.position).getAmount();

                editor.putInt("Id",list_id);
                editor.putString("Date", Date);
                editor.putString("Category", Category);
                editor.putString("Amount", String.valueOf(Amount));
                editor.commit();

                Intent i = new Intent(IncomeList.this, UpdateIncome.class);
                startActivity(i);
                finish();

            }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibBack:
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}



