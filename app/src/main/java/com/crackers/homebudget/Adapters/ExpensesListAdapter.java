package com.crackers.homebudget.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crackers.homebudget.Activities.ExpensesList;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Expenses;

import java.util.ArrayList;

/**
 * Created by Admin on 10-08-2016.
 */
public class ExpensesListAdapter extends BaseAdapter {

    Context mCtx;
    ArrayList<Expenses> expensesArrayList;

    public ExpensesListAdapter(ExpensesList context, ArrayList<Expenses> expensesArrayList) {
        this.mCtx=context;
        this.expensesArrayList=expensesArrayList;
    }

    @Override
    public int getCount() {
        return expensesArrayList.size();
    }

    //here i is position of array list
    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Expenses e=expensesArrayList.get(i);
        view= LayoutInflater.from(mCtx).inflate(R.layout.row_list_expenses,null);

        TextView tvCategory= (TextView) view.findViewById(R.id.tvCategory);
        TextView tvAmount= (TextView) view.findViewById(R.id.tvAmount);
        TextView tvDate= (TextView) view.findViewById(R.id.tvDate);

        tvCategory.setText("Category::"+e.getCategory());
        tvAmount.setText("Amount::"+e.getAmount());
        tvDate.setText("Date::"+e.getDate());
        return view;
    }
}
