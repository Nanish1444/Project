package console.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import console.model.Expense;
import console.model.Income;
import console.model.TransactionType;

public class CategoryDAO {
    
    private Connection connection;
    
    public CategoryDAO(Connection connection) {
        // Constructor to initialize the connection
        this.connection = connection;
    }

    public void addCategory(String categoryName, String categoryType) throws SQLException {
        String sql = "INSERT INTO categories (name, type) VALUES (?, ?)";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, categoryName);
        prest.setString(2, categoryType.toUpperCase());
        prest.executeUpdate();
    }
    
    public void editCategory(int categoryId, String categoryName, String categoryType) throws SQLException {
        String sql = "UPDATE categories SET name = ?, type = ? WHERE id = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, categoryName);
        prest.setString(2, categoryType.toUpperCase());
        prest.setInt(3, categoryId);
        prest.executeUpdate();
    }

    public void deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setInt(1, categoryId);
        prest.executeUpdate();
    }

    public List<Income> showSelectedIncomes(String category) throws SQLException {
        List<Income> incomes = new ArrayList<>();
        // Implementation for showing selected incomes by category
        String sql = "SELECT id FROM categories WHERE name = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, category);

        Integer id = null;
        ResultSet rs = prest.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        rs.close();
        if (id != null) {
            String incomeSql = "SELECT * FROM incomes WHERE category_id = ?";
            PreparedStatement incomePrest = connection.prepareStatement(incomeSql);
            incomePrest.setInt(1, id);
            ResultSet incomeRs = incomePrest.executeQuery();
            while (incomeRs.next()) {
                Income income = new Income();
                income.setIncomeId(incomeRs.getInt("id"));
                income.setIncomeDescription(incomeRs.getString("description"));
                income.setIncomeAmount(incomeRs.getDouble("amount"));
                income.setIncomeDate(incomeRs.getDate("date").toLocalDate());
                incomes.add(income);
            }
            incomeRs.close();
            incomePrest.close();
        } else {
            System.out.println("No category found with the name: " + category);
        }
        return incomes;
    }
    public List<String> showAllCategories(TransactionType type) throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT name FROM categories where type = ? ";
         // Assuming categories table has a column 'type' to differentiate between income and expense categories
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, type.name());
        ResultSet rs = prest.executeQuery();
        while (rs.next()) {
            categories.add(rs.getString("name"));
        }
        rs.close();
        return categories;
    }

    public List<Expense> showSelectedExpenses(String category) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        // Implementation for showing selected expenses by category
        String sql = "SELECT id FROM categories WHERE name = ?";
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, category);

        Integer id = null;
        ResultSet rs = prest.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        rs.close();
        if (id != null) {
            String expenseSql = "SELECT * FROM expenses WHERE category_id = ?";
            PreparedStatement expensePrest = connection.prepareStatement(expenseSql);
            expensePrest.setInt(1, id);
            ResultSet expenseRs = expensePrest.executeQuery();
            while (expenseRs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(expenseRs.getInt("id"));
                expense.setExpenseDescription(expenseRs.getString("description"));
                expense.setExpenseAmount(expenseRs.getDouble("amount"));
                expense.setExpenseDate(expenseRs.getDate("date").toLocalDate());
                expenses.add(expense);
            }
            expenseRs.close();
            expensePrest.close();
        } else {
            System.out.println("No category found with the name: " + category);
        }
        return expenses;
    } 
}
