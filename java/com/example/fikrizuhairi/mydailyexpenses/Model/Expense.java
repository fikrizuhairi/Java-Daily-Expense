package com.example.fikrizuhairi.mydailyexpenses.Model;

public class Expense {

    private int expenseId;
    private String expenseName;
    private String expensePrice;
    private String expenseDate;

    public Expense(){

    }

    public Expense(int expenseId, String expenseName, String expensePrice, String expenseDate){
        this.expenseId = expenseId;
        this.expenseName = expenseName;
        this.expensePrice = expensePrice;
        this.expenseDate = expenseDate;
    }

    public Expense(String expenseName, String expensePrice, String expenseDate){

        this.expenseName = expenseName;
        this.expensePrice = expensePrice;
        this.expenseDate = expenseDate;
    }

    public int getExpenseId(){
        return this.expenseId;
    }

    public void setExpenseId(int expenseId){
        this.expenseId = expenseId;
    }

    public String getExpenseName(){
        return this.expenseName;
    }

    public void setExpenseName(String expenseName){
        this.expenseName = expenseName;
    }

    public String getExpensePrice(){
        return this.expensePrice;
    }

    public void setExpensePrice(String expensePrice){
        this.expensePrice = expensePrice;
    }

    public String getExpenseDate(){
        return this.expenseDate;
    }

    public void setExpenseDate(String expenseDate){
        this.expenseDate = expenseDate;
    }
}
