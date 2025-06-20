package console.model;
// import database.model.TransactionType;

public class Category {
    private int id;
    
    private String categoryName;
    private TransactionType categoryType;

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Category(String categoryName, TransactionType categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public Category() {
    }

    public int getId() {    
        return id;
    }
    public void setId(int id) {
        this.id = id;
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