package com.crackers.homebudget.Utilities;

/**
 * Created by Prateek on 07-08-2016.
 */
public class Income {
    String Category,Date;
    Double Amount;
    int id;
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

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
}
