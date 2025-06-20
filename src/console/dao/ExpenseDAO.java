package console.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import console.model.Expense;

public class ExpenseDAO {
    private Connection connection;
    public ExpenseDAO(Connection connection) {
        // Constructor to initialize the connection
        this.connection = connection;
    }
    
    public void addExpense(Expense expense,int selectedType) throws SQLException {
        // Implementation for adding an expense
        String sql = "INSERT INTO expense (description, amount, date, type) VALUES (?, ?, ? , ?)";
        PreparedStatement prest = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(expense.getExpenseDate());
        prest.setString(1, expense.getExpenseDescription());   
        prest.setDouble(2, expense.getExpenseAmount());
        prest.setDate(3, sqlDate); 
        prest.setInt(4,selectedType);
        prest.executeUpdate();
    }


    public void editExpense(Expense expense) throws SQLException {
        // Implementation for editing an expense
        String sql = "UPDATE expense SET description = ?, amount = ?, date = ? WHERE id = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(expense.getExpenseDate());
        prest.setString(1, expense.getExpenseDescription()); 
        prest.setDouble(2, expense.getExpenseAmount());
        prest.setDate(3, sqlDate);
        prest.setInt(4, expense.getExpenseId());
        prest.executeUpdate();
    }


    public void softDeleteExpense(int expenseId) throws SQLException {
        String sql = "UPDATE expense SET is_deleted = TRUE WHERE id = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setInt(1, expenseId);
        prest.executeUpdate();
    }


    public void hardDeleteExpense(int expenseId) throws SQLException {
        String sql = "DELETE FROM expense WHERE id = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setInt(1, expenseId);
        prest.executeUpdate();
    }

    public Expense getExpense(int expenseId) throws SQLException {
        String sql = "select * from expense where id=?";
        PreparedStatement prest = connection.prepareStatement(sql);
        ResultSet rs = prest.executeQuery();
        Expense expense = new Expense();
        expense.setExpenseId(rs.getInt("id"));
        expense.setExpenseDescription(rs.getString("description"));
        expense.setExpenseAmount(rs.getDouble("amount"));
        expense.setExpenseDate(rs.getDate("date").toLocalDate());
        return expense;
    }

    public double getTotalExpense() throws SQLException {
        String sql = "SELECT * FROM expense WHERE is_deleted = FALSE";
        PreparedStatement prest = connection.prepareStatement(sql);
        ResultSet rs = prest.executeQuery();
        double amount=0.0;
        while(rs.next()){
            amount+=rs.getDouble("amount");
        }
        return amount;
    }

    public List<Expense> getAllExpenses() throws SQLException{
        List<Expense> expenses = new ArrayList<>();
        String sql = "select * from expense where is_deleted=false";
        PreparedStatement prest = connection.prepareStatement(sql);
        ResultSet rs = prest.executeQuery();
        while(rs.next()){
            Expense expense = new Expense();
            expense.setExpenseId(rs.getInt("id"));
            expense.setExpenseDescription(rs.getString("description"));
            expense.setExpenseAmount(rs.getDouble("amount"));
            expense.setExpenseDate(rs.getDate("date").toLocalDate());
            expenses.add(expense);
        }

        return expenses;

    }

    public List<Expense> getExpensesByMonth(int month, int year) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String sql = "select * from expense where MONTH(date) =? AND YEAR(date)=? AND is_deleted=false";
        PreparedStatement prest = connection.prepareStatement(sql);
        ResultSet rs = prest.executeQuery();
        while(rs.next()){
            Expense expense = new Expense();
            expense.setExpenseId(rs.getInt("id"));
            expense.setExpenseDescription(rs.getString("description"));
            expense.setExpenseAmount(rs.getDouble("amount"));
            expense.setExpenseDate(rs.getDate("date").toLocalDate());
            expenses.add(expense);
        }

        return expenses;
    }
}
