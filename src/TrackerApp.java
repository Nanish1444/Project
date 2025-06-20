
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
// import java.util.Locale.Category;

import console.model.Expense;
import console.model.Income;
import console.model.TransactionType;
import console.service.CategoryService;
import console.service.ExpenseService;
import console.service.IncomeService;
import console.service.UserService;
import console.model.Category;

public class TrackerApp {
   
    
    private static Scanner scan = new Scanner(System.in);

    private IncomeService incomeService = new IncomeService();
    private UserService userService = new UserService();
    private ExpenseService expenseService = new ExpenseService();
    private CategoryService categoryService = new CategoryService();


    public void start() throws SQLException {
        System.out.println("Welcome to Expense Tracker Application");
        System.out.println("Login or Signup to continue");
       
        boolean run = true;
        while (run) {
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline character

            switch(choice){
                case 1:
                    System.out.print("Enter your userMail: ");
                    String userMail = scan.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scan.nextLine();
                    if (userService.login(userMail,password)) {
                        System.out.println("Login successful!");
                        process();
                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                        start();
                    }
                    break;
                case 2:
                    System.out.print("Enter your email: ");
                    String email = scan.nextLine();
                    System.out.print("Enter your username: ");
                    String username1 = scan.nextLine();
                    System.out.print("Enter your password: ");
                    String password1 = scan.nextLine();
                    try {
                        if(userService.signup(email, username1, password1)){
                            System.out.println("Signup successful! You can now login.");
                            process();
                        }
                        else{
                            System.out.println("Signup failed. Please try again.");
                            start();
                        }
                        
                    } catch (SQLException e) {
                        System.out.println("Error during signup: " + e.getMessage());
                        start();
                    }
                    break;
                case 3:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue; // Prompt for choice again
            }
        }    
        
    }

    public void process() throws SQLException {
        System.out.println("Welcome to the main application!");
        System.out.println("Here you can manage your income and expenses.");
        

        System.out.print("Enter your choice: ");
        System.out.println("1. Goto Income Realted fildes  2. Goto Expense Related feilds 3.EXit ");
        boolean run = true;
        while(run){
            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline character
            switch(choice){
                case 1:
                    this.useIncome();
                    break;
                case 2:
                    this.useExpense();
                    break;
                case 3:
                    run=false;
                    break;
            }
            
        }

        
        
    }
    public void useIncome() throws SQLException{
        System.out.println("Using Income Service");
        System.out.println("===================================");
        
        System.out.println("1. Add Income");
        System.out.println("2. View All Incomes");
        System.out.println("3. Get Total Income");
        System.out.println("4. Get Incomes by Month");
        
        System.out.println("5. Show Incomes by Category");
        System.out.println("6. Update Income");
        System.out.println("7. Temporary delete Income");
        System.out.println("8. Permanently delete Income");
        System.out.println("9. List Available Categories for Incomes");
        System.out.println("10. Exit");

        // Consume newline character
        boolean run=true;
        while(run) {
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            scan.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Add a Description of Income");
                    String description = scan.nextLine();
                    System.out.println("Add the Amount of Income");
                    double amount = scan.nextDouble();
                    scan.nextLine(); // Consume newline character
                    System.out.println("Add the Date of Income (YYYY-MM-DD)");
                    String dateInput = scan.nextLine();
                    Income income = new Income(description, amount, dateInput);
                    List<Category> categories = categoryService.showAllCategories(TransactionType.INCOME);
                    

                    for(int i=0;i<categories.size();i++){
                        System.out.println((i+1)+" - "+categories.get(i));
                    }
                    System.out.println("Select the number of  Income Type :");

                    int selectedCategories = scan.nextInt();

                    int actualId = categories.get(selectedCategories - 1).getId();
                    
                    incomeService.addIncome(income,actualId);
                    break;
                case 2:
                    incomeService.getAllIncomes().forEach(incomeItem -> {
                        System.out.println("ID: " + incomeItem.getIncomeId() + ", Description: " + incomeItem.getIncomeDescription() +
                                ", Amount: " + incomeItem.getIncomeAmount() + ", Date: " + incomeItem.getIncomeDate());
                    });
                    break;
                case 3:
                    double totalIncome = incomeService.getTotalIncome();
                    if (totalIncome > 0) {
                        System.out.println("Total Income: " + totalIncome);
                    } else {
                        System.out.println("No income records found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter the month (1-12): ");
                    int month = scan.nextInt();
                    System.out.println("Enter the year (YYYY): ");
                    int year = scan.nextInt();
                    List<Income> incomesByMonth = incomeService.getIncomesByMonth(month, year); // Example for January 2023
                    System.out.println(incomesByMonth);
                    break;
                case 5:
                    System.out.println("Select the category number in the list");
                    List<Category> availableCategories= categoryService.showAllCategories(TransactionType.INCOME);

                    for(int i=0;i<availableCategories.size();i++){
                        System.out.println((i+1)+" : "+availableCategories.get(i));
                    }
                    int selectedNumber = scan.nextInt();
                    String category_selected = availableCategories.get(selectedNumber-1).getCategoryName();
                    List<Income> listedIncome = categoryService.showSelectedIncomes(category_selected);

                    for(int i=0;i<listedIncome.size();i++){
                        System.out.println(" ->  "+listedIncome.get(i));
                    }
                case 6:
                    System.out.println("Enter the ID of the income to update: ");
                    int incomeId = scan.nextInt();
                    scan.nextLine(); // Consume newline character
                    Income existingIncome = incomeService.getIncome(incomeId);
                    if (existingIncome != null) {
                        System.out.println("Current Description: " + existingIncome.getIncomeDescription());
                        System.out.println("Enter new Description (or press Enter to keep current): ");
                        String newDescription = scan.nextLine();
                        if (!newDescription.isEmpty()) {
                            existingIncome.setIncomeDescription(newDescription);
                        }
                        
                        System.out.println("Current Amount: " + existingIncome.getIncomeAmount());
                        System.out.println("Enter new Amount (or press Enter to keep current): ");
                        String amountInput = scan.nextLine();
                        if (!amountInput.isEmpty()) {
                            existingIncome.setIncomeAmount(Double.parseDouble(amountInput));
                        }
                        
                        System.out.println("Current Date: " + existingIncome.getIncomeDate());
                        System.out.println("Enter new Date (YYYY-MM-DD) (or press Enter to keep current): ");
                        String dateUpdate = scan.nextLine();
                        if (!dateUpdate.isEmpty()) {
                            existingIncome.setIncomeDate(LocalDate.parse(dateUpdate));
                        }
                        
                        incomeService.updateIncome(existingIncome);
                    } else {
                        System.out.println("No income found with ID: " + incomeId);
                    }
                    break;
                case 7:
                    System.out.println("Enter the ID of the income to temporarily delete: ");
                    int tempDeleteId = scan.nextInt();
                    incomeService.temporaryDelete(tempDeleteId);
                    System.out.println("Income with ID " + tempDeleteId + " has been temporarily deleted.");
                    break;
                case 8:
                    System.out.println("Enter the ID of the income to permanently delete: ");
                    int permDeleteId = scan.nextInt();
                    incomeService.permanentDelete(permDeleteId);
                    System.out.println("Income with ID " + permDeleteId + " has been permanently deleted.");
                    break;
                case 9:
                    System.out.println("List of category type Available for incomes: ");

                    List<Category> listCategories= categoryService.showAllCategories(TransactionType.INCOME);

                    for(int i=0;i<listCategories.size();i++){
                        System.out.println((i+1)+" : "+listCategories.get(i).getCategoryName());
                    }
                    break;
                case 10:
                    run=false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
   public void useExpense() throws SQLException{
        System.out.println("Using Expense Service");
        System.out.println("===================================");

        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Get Total Expense");
        System.out.println("4. Get Expenses by Month");

        System.out.println("5. Show Expenses by Category");
        System.out.println("6. Update Expense");
        System.out.println("7. Temporary delete Expense");
        System.out.println("8. Permanently delete Expense");
        System.out.println("9. List Available Categories for Expenses");
        System.out.println("10. Exit");
        
        boolean run = true;
        while (run) {
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Add a Description of Expense");
                    String description = scan.nextLine();
                    System.out.println("Add the Amount of Expense");
                    double amount = scan.nextDouble();
                    scan.nextLine();  
                    System.out.println("Add the Date of Expense (YYYY-MM-DD)");
                    String dateInput = scan.nextLine();
                    Expense expense = new Expense(description, amount, dateInput);
                    List<Category> categories = categoryService.showAllCategories(TransactionType.EXPENSE);

                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + " - " + categories.get(i).getCategoryName());
                    }
                    System.out.println("Select the number of Expense Type :");


                    int selectedCategories = scan.nextInt();

                    int actualId = categories.get(selectedCategories - 1).getId();
                    System.out.println("ACTUAL id of epense id : "+actualId);
                    expenseService.addExpense(expense, actualId);
                    System.out.println("Expense added successfully!");
                    break;
                case 2:
                    expenseService.getAllExpenses().forEach(expenseItem -> {
                        System.out.println("ID: " + expenseItem.getExpenseId() + ", Description: " + expenseItem.getExpenseDescription() +
                                ", Amount: " + expenseItem.getExpenseAmount() + ", Date: " + expenseItem.getExpenseDate());
                    });
                    break;
                case 3:
                    double totalExpense = expenseService.getTotalExpense();
                    if (totalExpense > 0) {
                        System.out.println("Total Expense: " + totalExpense);
                    } else {
                        System.out.println("No expense records found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter the month (1-12): ");
                    int month = scan.nextInt();
                    System.out.println("Enter the year (YYYY): ");
                    int year = scan.nextInt();
                    List<Expense> expensesByMonth = expenseService.getExpensesByMonth(month, year);
                    System.out.println(expensesByMonth);
                    break;
                case 5:
                    System.out.println("Select the category number in the list");
                    List<Category> availableCategories = categoryService.showAllCategories(TransactionType.EXPENSE);

                    for (int i = 0; i < availableCategories.size(); i++) {
                        System.out.println((i + 1) + " : " + availableCategories.get(i));
                    }
                    int selectedNumber = scan.nextInt();
                    String category_selected = availableCategories.get(selectedNumber - 1).getCategoryName();
                    List<Expense> listedExpense = categoryService.showSelectedExpense(category_selected);

                    for (int i = 0; i < listedExpense.size(); i++) {
                        System.out.println(" ->  " + listedExpense.get(i));
                    }
                    break;
                case 6:
                    System.out.println("Enter the ID of the expense to update: ");
                    int expenseId = scan.nextInt();
                    scan.nextLine();
                    Expense existingExpense = expenseService.getExpense(expenseId);
                    if (existingExpense != null) {
                        System.out.println("Current Description: " + existingExpense.getExpenseDescription());
                        System.out.println("Enter new Description (or press Enter to keep current): ");
                        String newDescription = scan.nextLine();
                        if (!newDescription.isEmpty()) {
                            existingExpense.setExpenseDescription(newDescription);
                        }

                        System.out.println("Current Amount: " + existingExpense.getExpenseAmount());
                        System.out.println("Enter new Amount (or press Enter to keep current): ");
                        String amountInput = scan.nextLine();
                        if (!amountInput.isEmpty()) {
                            existingExpense.setExpenseAmount(Double.parseDouble(amountInput));
                        }

                        System.out.println("Current Date: " + existingExpense.getExpenseDate());
                        System.out.println("Enter new Date (YYYY-MM-DD) (or press Enter to keep current): ");
                        String dateUpdate = scan.nextLine();
                        if (!dateUpdate.isEmpty()) {
                            existingExpense.setExpenseDate(LocalDate.parse(dateUpdate));
                        }

                        expenseService.editExpense(existingExpense);
                        } else {
                            System.out.println("No expense found with ID: " + expenseId);
                        }
                    break;
                case 7:
                    System.out.println("Enter the ID of the expense to temporarily delete: ");
                    int tempDeleteId = scan.nextInt();
                    expenseService.temporaryDelete(tempDeleteId);
                    System.out.println("Expense with ID " + tempDeleteId + " has been temporarily deleted.");
                    break;
                case 8:
                    System.out.println("Enter the ID of the expense to permanently delete: ");
                    int permDeleteId = scan.nextInt();
                    expenseService.permanentDelete(permDeleteId);
                    System.out.println("Expense with ID " + permDeleteId + " has been permanently deleted.");
                    break;
                case 9:
                    System.out.println("List of category type Available for expenses: ");

                    List<Category> listCategories = categoryService.showAllCategories(TransactionType.EXPENSE);

                    for (int i = 0; i < listCategories.size(); i++) {
                        System.out.println((i + 1) + " : " + listCategories.get(i).getCategoryName());
                    }
                    break;
                case 10:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
   }
    
    
}
