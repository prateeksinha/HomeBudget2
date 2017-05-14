package com.crackers.homebudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.ULocale;

import com.crackers.homebudget.Utilities.Income;

import java.util.Date;

/**
 * Created by Prateek on 31-07-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String DBName="BudgetDB";
    public final static String TBLName="BudgetIncomeTBL";
    public final static String ETBLName="BudgetExpensesTBL";
    public final static String STBLName="SignUpTable";
    SQLiteDatabase db=null;
    String Date,Amount,Category;

    public DBHelper(Context context){
        super(context,DBName ,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table SignUpTable("+
                "UserName Text Primary Key ,"+
                "Password Text NOT NULL,"+
                "Name Text NOT NULL)");
        sqLiteDatabase.execSQL("Create table BudgetIncomeTBL("+
                "Id Integer Primary Key AutoIncrement ,"+
                "UserName Text NOT NULL,"+
                "Category  Text NOT NULL,"+
                "Amount  Real NOT NULL,"+
                "Date Date NOT NULL)");
        sqLiteDatabase.execSQL("Create table BudgetExpensesTBL("+
                "Id Integer Primary Key AutoIncrement ,"+
                "UserName Text NOT NULL,"+
                "Category Text NOT NULL,"+
                "Amount Real NOT NULL,"+
                "Date Date NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public long signUpRec(String UserName,String Password,String Name){
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("UserName",UserName);
        cv.put("Password",Password);
        cv.put("Name",Name);
        long id=db.insert("SignUpTable",null,cv);
        return id;
    }
    public boolean logIn(String Username,String Password){
        db=this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM SignUpTable WHERE UserName=? AND Password=?",new String[]{Username,Password});
        if(c!=null){
            if(c.getCount()>0){
                return true;
            }
        }
        return false;
    }

    public long insertIncomeRec(String UserName,String Category, Double Amount, String Date){
        db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("UserName",UserName);
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        cv.put("Date",Date);
        long id=db.insert(TBLName,null,cv);
        return id;

    }
    public long insertExpensesRec(String UserName,String Category, Double Amount, String Date){
        db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("UserName",UserName);
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        cv.put("Date",Date);
        long id=db.insert(ETBLName,null,cv);
        return id;
    }
    public long deleteRec(String Date,Double Amount,String Category){
        db =this.getWritableDatabase();
        long id=db.delete(TBLName,"Date=? AND Amount=? AND Category=?",new String[]{Date, String.valueOf(Amount),Category});
        return id;
    }
    public long updateRecInc(int id,String Date,Double Amount,String Category){
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Date",Date);
        cv.put("id",id);
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        long i=db.update(TBLName,cv ,"Id="+id,new String[]{});
        return i;
    }

    public long deleteRecord(String Date,Double Amount,String Category){
        db =this.getWritableDatabase();
        long id=db.delete(ETBLName,"Date=? AND Amount=? AND Category=?",new String[]{Date, String.valueOf(Amount),Category});
        return id;
    }

    public long updateRecExp(int id, String Category, Double Amount,String Date){
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Date",Date);
        cv.put("id",id);
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        long i=db.update(ETBLName,cv ,"Id="+id,new String[]{});
        return i;
    }

    public String getDate(){ return Date; }
    public Double getAmount(){
        return Double.valueOf(Amount);
    }
    public String getCategory(){
        return Category;
    }
}
