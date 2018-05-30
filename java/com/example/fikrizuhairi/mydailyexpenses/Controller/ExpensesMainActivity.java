package com.example.fikrizuhairi.mydailyexpenses.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fikrizuhairi.mydailyexpenses.Model.Expense;
import com.example.fikrizuhairi.mydailyexpenses.Database.ExpensesDB;
import com.example.fikrizuhairi.mydailyexpenses.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExpensesMainActivity extends AppCompatActivity {

    EditText editTextExpenses, editTextPrices, editTextDate;
    Button buttonSave;

    SQLiteDatabase databaseMyExpenses;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");

    private ExpensesDB expenseDb;

    private String expenseName, expensePrice, expenseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextExpenses = findViewById(R.id.edit_text_expenses);
        editTextPrices = findViewById(R.id.edit_text_price);
        editTextDate = findViewById(R.id.edit_text_date);
        buttonSave = findViewById(R.id.button_save);

        getCurrentDate();

        expenseDb = new ExpensesDB(this);
        databaseMyExpenses = openOrCreateDatabase(expenseDb.DATABASE_NAME, MODE_PRIVATE, null);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextExpenses.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"Do not leave expense name blank", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }else if(TextUtils.isEmpty(editTextPrices.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"Do not leave expense price blank", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }else{
                    saveToDatabase(view);
                }

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateNewDate();
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DatePickerDialog(ExpensesMainActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void saveToDatabase(final View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getInputValues();

                expenseDb.addExpense(new Expense(expenseName, expensePrice, expenseDate));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(getApplicationContext(),"Expense Information saved successfully", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        };

        Thread threadSave = new Thread(runnable);
        threadSave.start();
    }

    private void getInputValues(){
        expenseName = editTextExpenses.getText().toString();
        expensePrice = editTextPrices.getText().toString();
        expenseDate = editTextDate.getText().toString();
    }

    private void getCurrentDate() {
        String stringDate = simpleDateFormat.format(calendar.getTime());
        editTextDate.setText(stringDate);
    }

    private void updateNewDate() {
        editTextDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expenses_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intentActivity = new Intent(this, ActivityExpenseList.class);
            startActivity(intentActivity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
