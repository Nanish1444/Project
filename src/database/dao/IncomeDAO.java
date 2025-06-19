package database.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.model.Income;

public class IncomeDAO {
    // add edit delete filter search show all
    // This class will handle the database operations for Income objects
    // It will use the Connection_DB class to get a connection to the database
    private Connection connection=null;

    public IncomeDAO(Connection connection) {
        // Constructor to initialize the connection
        this.connection = connection;
    }

    public void addIncome(Income income,int selectedType) throws SQLException{
        String sql = "INSERT INTO income (description, amount, date, categories) VALUES (?, ?, ?, ?)";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(income.getIncomeDate());

        prest.setString(1,income.getIncomeDescription());
        prest.setDouble(2,income.getIncomeAmount());
        prest.setDate(3,sqlDate);
        prest.setInt(4,selectedType);
        prest.executeUpdate();
        System.out.println("Income added Sucessfully");
    }

    public void editIncome(Income income) throws SQLException {
        String sql = "UPDATE income SET description = ?, amount = ?, date = ? WHERE id = ?";

        PreparedStatement prest = connection.prepareStatement(sql);
        java.sql.Date sqlDate = java.sql.Date.valueOf(income.getIncomeDate());
        prest.setInt(4,income.getIncomeId()); // Assuming Income has a method getIncomeId()
        prest.setString(1,income.getIncomeDescription());
        prest.setDouble(2, income.getIncomeAmount());
        prest.setDate(3, sqlDate);
        prest.executeUpdate();
        System.out.println("Income updated successfully");
    }

    public void softDeleteIncome(int incomeId) throws SQLException {
        String sql = "UPDATE income SET is_deleted = TRUE WHERE id = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setInt(1, incomeId);
        prest.executeUpdate();
        System.out.println("Income soft deleted successfully");
    }

    public void hardDeleteIncome(int incomeId) throws SQLException {
        String sql = "DELETE FROM income WHERE id = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setInt(1, incomeId);
        prest.executeUpdate();
        System.out.println("Income hard deleted successfully");
    }
    public List<Income> filterIncomeByDate(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE date BETWEEN ? AND ?";
        
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setDate(1, java.sql.Date.valueOf(startDate));
        prest.setDate(2, java.sql.Date.valueOf(endDate));
        ResultSet rs = prest.executeQuery();

        while(rs.next()){
            Income income = new Income();
            income.setIncomeId(rs.getInt("id"));
            income.setIncomeDescription(rs.getString("description"));
            income.setIncomeAmount(rs.getDouble("amount"));
            income.setIncomeDate(rs.getDate("date").toLocalDate());
            // Add the income to a list or process it as needed
            incomes.add(income);
        }
        System.out.println("Filtered incomes by date successfully");
        return incomes;
    }
    
    public List<Income> showAllIncomes() throws SQLException {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE is_deleted = FALSE";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        ResultSet rs = prest.executeQuery();

        while(rs.next()){
            Income income = new Income();
            income.setIncomeId(rs.getInt("id"));
            income.setIncomeDescription(rs.getString("description"));
            income.setIncomeAmount(rs.getDouble("amount"));
            income.setIncomeDate(rs.getDate("date").toLocalDate());
            // Add the income to a list or process it as needed
            incomes.add(income);
        }
        System.out.println("All incomes shown successfully");
        return incomes;
    }

    public Income getIncome(int incomeId) throws SQLException {
        String sql = "SELECT * FROM income WHERE id = ? AND is_deleted = FALSE";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setInt(1, incomeId);
        
        ResultSet rs = prest.executeQuery();
        Income income = null;
        if (rs.next()) {
            income = new Income();
            income.setIncomeId(rs.getInt(1));
            income.setIncomeDescription(rs.getString(2));
            income.setIncomeAmount(rs.getDouble(3));
            income.setIncomeDate(rs.getDate(4).toLocalDate());
        }
        
        return income;
        
    }
    public List<Income> getAllIncomes() throws SQLException {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE is_deleted = FALSE";
        PreparedStatement prest = connection.prepareStatement(sql);
        ResultSet rs = prest.executeQuery();
        while (rs.next()) {
            Income income = new Income();
            income.setIncomeId(rs.getInt(1));
            income.setIncomeDescription(rs.getString(2));
            income.setIncomeAmount(rs.getDouble(3));
            income.setIncomeDate(rs.getDate(4).toLocalDate());
            incomes.add(income);
        }

        return null;
    }

    public double getTotalIncome() {
        double totalIncome = 0.0;
        String sql = "SELECT SUM(amount) FROM income WHERE is_deleted = FALSE";
        try (PreparedStatement prest = connection.prepareStatement(sql);
                ResultSet rs = prest.executeQuery()) {
                if (rs.next()) {
                    totalIncome = rs.getDouble(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return totalIncome;
    }

    public List<Income> getIncomesByMonth(int month, int year) {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE MONTH(date) = ? AND YEAR(date) = ? AND is_deleted = FALSE";
        
        try (PreparedStatement prest = connection.prepareStatement(sql)) {
            prest.setInt(1, month);
            prest.setInt(2, year);
            ResultSet rs = prest.executeQuery();
            
            while (rs.next()) {
                Income income = new Income();
                income.setIncomeId(rs.getInt("id"));
                income.setIncomeDescription(rs.getString("description"));
                income.setIncomeAmount(rs.getDouble("amount"));
                income.setIncomeDate(rs.getDate("date").toLocalDate());
                incomes.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        if (incomes.isEmpty()) {
            System.out.println("No incomes found for the specified month and year.");
        }
        return incomes;
    }

    
    
}
