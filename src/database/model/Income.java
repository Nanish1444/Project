package database.model;

import java.time.LocalDate;

// import java.time.LocalDate;
public class Income {
    private int incomeId;
    private String incomeDescription;
    private double incomeAmount;
    private String incomeDate;

    public Income(String incomeDescription, double incomeAmount, String incomeDate) {
        this.incomeDescription = incomeDescription;
        this.incomeAmount = incomeAmount;
        this.incomeDate = incomeDate;
    }

    public Income() {
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate.toString();
    }
    public int getIncomeId() {
        return incomeId;
    }
    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public String toString() {
        return "Income{" +
                "incomeId=" + incomeId +
                ", incomeDescription='" + incomeDescription + '\'' +
                ", incomeAmount=" + incomeAmount +
                ", incomeDate=" + incomeDate +
                '}';
    }

    
}
