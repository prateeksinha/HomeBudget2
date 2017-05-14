package com.crackers.homebudget.Utilities;

/**
 * Created by Prateek on 11-08-2016.
 */
public class Expenses {
    String Category,Date;
    Double Amount;
    int data_id;

    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category){
        this.Category=Category;
    }

    public Double getAmount(){
        return Amount;
    }
    public void setAmount(Double Amount){
        this.Amount=Amount;
    }

    public String getDate(){
        return Date;
    }
    public void setDate(String Date){
        this.Date=Date;
    }

    public int getId(){return data_id;}
    public void setData_id(int data_id){
        this.data_id=data_id;
    }

}
