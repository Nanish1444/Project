package database.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import database.model.Expense;

public class ExpenseDAO {
    private Connection connection;
    ExpenseDAO(Connection connection) {
        // Constructor to initialize the connection
        this.connection = connection;
    }
    
    public void addExpense(Expense expense) throws SQLException {
        // Implementation for adding an expense
        String sql = "INSERT INTO expense (description, amount, date) VALUES (?, ?, ?)";
        PreparedStatement prest = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(expense.getExpenseDate());
        prest.setString(1, expense.getExpenseDescription());   
        prest.setDouble(2, expense.getExpenseAmount());
        prest.setDate(3, sqlDate); 
        prest.executeUpdate();
    }


    public void editExpense(int expenseId, String description, double amount, LocalDate date) throws SQLException {
        // Implementation for editing an expense
        String sql = "UPDATE expense SET description = ?, amount = ?, date = ? WHERE id = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        prest.setInt(1, expenseId); 
        prest.setString(2, description);
        prest.setDouble(3, amount);
        prest.setDate(4, sqlDate);
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

    public void filterExpenseByDate(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM expense WHERE date BETWEEN ? AND ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setDate(1, java.sql.Date.valueOf(startDate));
        prest.setDate(2, java.sql.Date.valueOf(endDate));
        prest.executeQuery();
    }
    public void showAllExpenses() throws SQLException {
        String sql = "SELECT * FROM expense WHERE is_deleted = FALSE";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.executeQuery();
    }
}
