package console.service;

import java.sql.SQLException;
import java.util.List;

import console.dao.CategoryDAO;
import console.database.Connection_DB;
import console.model.Category;
import console.model.Expense;
import console.model.Income;
import console.model.TransactionType;

public class CategoryService {
    CategoryDAO categoriesDAO = new CategoryDAO(Connection_DB.getInstanceDB());

    public CategoryService(){}

    public void addCategory(String categoryName,String categoryType ) throws SQLException{
        this.categoriesDAO.addCategory(categoryName, categoryType);
    }
    public void editCategory(int id,String name,String type) throws SQLException{
        this.categoriesDAO.editCategory(id, name, type);
    }
    public void deleteCategory(int categoriesId) throws SQLException{
        this.categoriesDAO.deleteCategory(categoriesId);
    }
    public List<Income> showSelectedIncomes(String categoryName) throws SQLException{
        return this.categoriesDAO.showSelectedIncomes(categoryName);
    }

    public List<Expense> showSelectedExpense(String categoryName) throws SQLException{
        return this.categoriesDAO.showSelectedExpenses(categoryName);
    }
    public List<Category> showAllCategories(TransactionType type) throws SQLException{
        return this.categoriesDAO.showAllCategories(type);
    }

    



    
}
