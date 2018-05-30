package com.example.fikrizuhairi.mydailyexpenses.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fikrizuhairi.mydailyexpenses.Model.Expense;

public class ExpensesDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyExpenses";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Expenses";
    public static final String COLUMN_NAME = "expense_name";
    public static final String COLUMN_PRICE = "expense_price";
    public static final String COLUMN_DATE = "expense_date";
    public static final String COLUMN_ID = "expense_id";

    public ExpensesDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_EXPENSES = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " VARCHAR,"
                + COLUMN_PRICE + " VARCHAR,"
                + COLUMN_DATE + " DATE );";

        db.execSQL(CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_EXPENSES = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(DROP_TABLE_EXPENSES);
        onCreate(db);
    }

    public Cursor getDataById(int id){
        String GET_TABLE_DATA_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "= " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_TABLE_DATA_BY_ID, null );
        return cursor;
    }

    public Cursor getAllData(){
        String GET_ALL_TABLE_DATA = "SELECT * FROM " + TABLE_NAME + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor expense = db.rawQuery(GET_ALL_TABLE_DATA, null );
        return expense;
    }

    public void addExpense(Expense expense){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, expense.getExpenseName());
        values.put(COLUMN_PRICE, expense.getExpensePrice());
        values.put(COLUMN_DATE, expense.getExpenseDate());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void executeCustomSQL(String stringSQL, Context applicationContext){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(stringSQL);
        }catch (Exception e){
            Log.d("Unable to run query", "error!");
        }
    }

    public int getTotalRow(){
        int numberOfRow;
        SQLiteDatabase db = this.getReadableDatabase();
        numberOfRow = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numberOfRow;
    }
}
