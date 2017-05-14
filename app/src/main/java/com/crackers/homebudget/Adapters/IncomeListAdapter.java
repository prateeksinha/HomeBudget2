package com.crackers.homebudget.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Income;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by Prateek on 06-08-2016.
 */
public class IncomeListAdapter extends BaseAdapter {
    Context mCtx;
    ArrayList<Income> incomeArrayList;

    public IncomeListAdapter(Context context,ArrayList<Income> incomeArrayList){
        this.mCtx = context;
        this.incomeArrayList= incomeArrayList;

    }
    @Override
    public int getCount() {
        return incomeArrayList.size();
    }

    @Override
    //here i denote the position of array list;
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        Income income=incomeArrayList.get(i);
        view = LayoutInflater.from(mCtx).inflate(R.layout.row_list_income,null);
        TextView tvCategory= (TextView) view.findViewById(R.id.tvCategory);
        TextView tvAmount= (TextView) view.findViewById(R.id.tvAmount);
        TextView tvDate= (TextView) view.findViewById(R.id.tvDate);
        tvCategory.setText("Category::"+income.getCategory());
        tvAmount.setText("Amount::"+income.getAmount());
        tvDate.setText("Date::"+income.getDate());
        return view;
    }

}
