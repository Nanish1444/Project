package console.service;
import java.sql.SQLException;
import java.util.List;
import console.dao.ExpenseDAO;
import console.database.Connection_DB;
import console.model.Expense;

public class ExpenseService {
    ExpenseDAO expense = new ExpenseDAO(Connection_DB.getInstanceDB());
    public ExpenseService() {
        
    }
    
    public void addExpense(Expense expense,int selectedType) throws SQLException {
        this.expense.addExpense(expense,selectedType);
    }
    public void editExpense(Expense expense) throws SQLException {
        this.expense.editExpense(expense);
    }
    public void temporaryDelete(int expenseId) throws SQLException{
        this.expense.softDeleteExpense(expenseId);
    }
    public void permanentDelete(int expenseId) throws SQLException{
        this.expense.hardDeleteExpense(expenseId);
    }
    public Expense getExpense(int expenseId)throws SQLException {
        return this.expense.getExpense(expenseId);
    }
    public List<Expense> getAllExpenses()throws SQLException {
        return this.expense.getAllExpenses();
    }
    
    public List<Expense> getExpensesByMonth(int month, int year) throws SQLException {
        return this.expense.getExpensesByMonth(month, year);
    }
    
    public double getTotalExpense() throws SQLException{
        return this.expense.getTotalExpense();
    }
}
