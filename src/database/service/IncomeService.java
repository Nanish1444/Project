package database.service;
import database.model.Income;
import database.Connection_DB;
import database.dao.IncomeDAO;
import java.sql.SQLException;
import java.util.List;

public class IncomeService {
    
    IncomeDAO income = new IncomeDAO(Connection_DB.getInstanceDB());
    public IncomeService() {
        
    }

    public void addIncome(Income income,int selectedType) throws SQLException {
        this.income.addIncome(income,selectedType);
    }
    public void updateIncome(Income income) throws SQLException {
        this.income.editIncome(income);
    }
    public void temporaryDelete(int incomeId) throws SQLException{
        this.income.softDeleteIncome(incomeId);
    }
    public void permanentDelete(int incomeId) throws SQLException{
        this.income.hardDeleteIncome(incomeId);
    }
    public Income getIncome(int incomeId)throws SQLException {
        return this.income.getIncome(incomeId);
    }
    public List<Income> getAllIncomes()throws SQLException {
        return this.income.getAllIncomes();
    }
    
    public List<Income> getIncomesByMonth(int month, int year) {
        return this.income.getIncomesByMonth(month, year);
    }
    
    public double getTotalIncome() throws SQLException{
        return this.income.getTotalIncome();
    }
}
