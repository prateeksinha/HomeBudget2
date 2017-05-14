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

import com.crackers.homebudget.Adapters.ExpensesListAdapter;
import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Expenses;

import java.util.ArrayList;

/**
 * Created by Prateek on 10-08-2016.
 */
public class ExpensesList extends Activity implements View.OnClickListener {

    ArrayList<Expenses> expensesArrayList;
    ArrayAdapter expensesArrayAdapter;
    ListView lvExpenses;

    DBHelper dbHelper=null;
    SQLiteDatabase sqLiteDataBase;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ImageButton ibBack;
    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_list);

        sharedPreferences = getSharedPreferences("prefName", MODE_APPEND);
        editor = sharedPreferences.edit();

        expensesArrayList=new ArrayList<Expenses>();
        dbHelper=new DBHelper(getApplicationContext());
        lvExpenses= (ListView) findViewById(R.id.lvExpenses);
        lvExpenses.setAdapter(new ArrayAdapter<Expenses>(this,R.layout.expenses_list));
        expensesArrayAdapter=new ArrayAdapter<Expenses>(getApplicationContext(),R.layout.expenses_list,R.id.tvCategory,expensesArrayList);

        String userName=sharedPreferences.getString("userName",null);
        getDataFromDB(userName);

        ibBack= (ImageButton) findViewById(R.id.ibBack);
        ExpensesListAdapter adapter=new ExpensesListAdapter(ExpensesList.this,expensesArrayList);
        lvExpenses.setAdapter(adapter);

        eventHandling();
    }

    private void eventHandling() {

        ibBack.setOnClickListener(this);
        registerForContextMenu(lvExpenses);
    }


    private void getDataFromDB(String userName) {

        sqLiteDataBase=dbHelper.getReadableDatabase();
        Cursor  cursor=sqLiteDataBase.rawQuery("Select * from BudgetExpensesTBL Where UserName=?",new String[]{userName});
        if(cursor.moveToFirst()){
            do{
                Expenses e=new Expenses();
                e.setData_id(cursor.getInt(Id));
                e.setCategory(cursor.getString(cursor.getColumnIndex("Category")));
                e.setAmount(cursor.getDouble(cursor.getColumnIndex("Amount")));
                e.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                ((BaseAdapter)lvExpenses.getAdapter()).notifyDataSetChanged();
                expensesArrayAdapter.notifyDataSetChanged();
                expensesArrayList.add(e);
            }
            while (cursor.moveToNext());
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0,1,0,"Delete");
        menu.add(0,2,1,"Edit");
    }
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId()==1){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

            final String Category=expensesArrayList.get(info.position).getCategory();
            final String Date=expensesArrayList.get(info.position).getDate();
            final Double Amount=expensesArrayList.get(info.position).getAmount();

            long id=dbHelper.deleteRecord(Date,Amount,Category);
            if (id!=-1){
                Intent in=new Intent(getApplicationContext(),ExpensesList.class);
                startActivity(in);
                finish();
            }
        }
        else if (item.getItemId() == 2) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            final int list_id=expensesArrayList.get(info.position).getId();
            final String Date = expensesArrayList.get(info.position).getDate();
            final String Category = expensesArrayList.get(info.position).getCategory();
            final Double Amount = expensesArrayList.get(info.position).getAmount();

            editor.putInt("Id",list_id);
            editor.putString("Date", Date);
            editor.putString("Category", Category);
            editor.putString("Amount", String.valueOf(Amount));
            editor.commit();

            Intent i = new Intent(ExpensesList.this, UpdateExpenditure.class);
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
