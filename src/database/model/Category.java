package database.model;
// import database.model.TransactionType;

public class Category {
    private String categoryName;
    private TransactionType categoryType;

    public Category(String categoryName, TransactionType categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType.name();
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType.toUpperCase().equals("INCOME") ? TransactionType.INCOME : TransactionType.EXPENSE;
    }


    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}