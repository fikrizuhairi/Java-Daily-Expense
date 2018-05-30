package com.example.fikrizuhairi.mydailyexpenses.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fikrizuhairi.mydailyexpenses.Model.Expense;
import com.example.fikrizuhairi.mydailyexpenses.R;

import java.util.ArrayList;
import java.util.List;

public class ExpensesDataAdapter extends ArrayAdapter<Expense> {
    Context context;
    ArrayList<Expense> expenses;

    //the layout resource file for the list items
    int resource;
    List<Expense> expenseList;

    //constructor initializing the values
    public ExpensesDataAdapter(Context context, int resource, List<Expense> heroList) {
        super(context, resource, heroList);
        this.context = context;
        this.resource = resource;
        this.expenseList = heroList;
    }


    public ExpensesDataAdapter(Context context, ArrayList<Expense> expense){
        super(context, R.layout.expenses_list_view, expense);
        this.context = context;
        this.expenses = expense;
    }

    public  class  Holder{
        TextView textViewExpenseName;
        TextView textViewExpensePrice;
        TextView textViewExpenseDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Expense data = getItem(position);

        Holder viewHolder;

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.expenses_list_view, parent, false);

            viewHolder.textViewExpenseName = convertView.findViewById(R.id.text_view_expense_name);
            viewHolder.textViewExpensePrice = convertView.findViewById(R.id.text_view_expense_price);
            viewHolder.textViewExpenseDate = convertView.findViewById(R.id.text_view_expense_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        viewHolder.textViewExpenseName.setText(data.getExpenseName());
        viewHolder.textViewExpensePrice.setText("RM "+data.getExpensePrice());
        viewHolder.textViewExpenseDate.setText(data.getExpenseDate());

        // Return the completed view to render on screen
        return convertView;
    }
}
