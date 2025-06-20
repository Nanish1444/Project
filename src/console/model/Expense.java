package console.model;

import java.time.LocalDate;

public class Expense {
    private int expenseId;
    private String expenseDescription;
    private double expenseAmount;
    private String expenseDate;

    public Expense(String expenseDescription, double expenseAmount, String expenseDate) {
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }

    public Expense() {
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate.toString();
    }

    public int getExpenseId() {
        return expenseId;
    }
    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }


    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", expenseDescription='" + expenseDescription + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", expenseDate=" + expenseDate +
                '}';
    }

}
