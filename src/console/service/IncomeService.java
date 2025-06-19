package console.service;
import java.sql.SQLException;
import java.util.List;

import console.dao.IncomeDAO;
import console.database.Connection_DB;
import console.model.Income;

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
