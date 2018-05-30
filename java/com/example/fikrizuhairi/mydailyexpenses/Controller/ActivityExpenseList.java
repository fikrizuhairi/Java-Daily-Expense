package com.example.fikrizuhairi.mydailyexpenses.Controller;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fikrizuhairi.mydailyexpenses.Database.ExpensesDB;
import com.example.fikrizuhairi.mydailyexpenses.Model.Expense;
import com.example.fikrizuhairi.mydailyexpenses.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ActivityExpenseList extends AppCompatActivity {

    TextView textViewTotalPrice;

    private ExpensesDB expenseDb;
    private String expenseName, expensePrice, expenseDate;
    private Double totalPrice;
    private ListView listViewExpenses;
    private List<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expenseDb = new ExpensesDB(this);
        totalPrice = 0.00;

        expenseList = new ArrayList<>();
        listViewExpenses = findViewById(R.id.list_view_expenses);
        textViewTotalPrice = findViewById(R.id.text_view_total);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Cursor resultSet = expenseDb.getAllData();
                Expense expense;

                if(resultSet.moveToFirst()){
                    do{
                        expenseName = resultSet.getString(resultSet.getColumnIndex(expenseDb.COLUMN_NAME));
                        expensePrice = resultSet.getString(resultSet.getColumnIndex(expenseDb.COLUMN_PRICE));
                        expenseDate = resultSet.getString(resultSet.getColumnIndex(expenseDb.COLUMN_DATE));

                        //calculate total price
                        Double price = Double.parseDouble(expensePrice);
                        totalPrice = totalPrice + price;

                        //round up the inserted price
                        Double roundedPrice =  round(price, 2);
                        String roundedPriceString = Double.toString(roundedPrice);

                        expense = new Expense(expenseName, roundedPriceString, expenseDate);
                        expenseList.add(expense);


                    }while (resultSet.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ExpensesDataAdapter adapter = new ExpensesDataAdapter(ActivityExpenseList.this, R.layout.expenses_list_view, expenseList);
                        listViewExpenses.setAdapter(adapter);
                        textViewTotalPrice.setText("Total Expenses: RM " + round(totalPrice, 2));
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



}
