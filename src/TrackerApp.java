
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
// import java.util.Locale.Category;

import console.dao.CategoryDAO;
import console.database.Connection_DB;
import console.model.Income;
import console.model.TransactionType;
import console.service.IncomeService;
import console.service.UserService;

public class TrackerApp {
   
    
    private static Scanner scan = new Scanner(System.in);


    IncomeService incomeService = new IncomeService();
    UserService userService = new UserService();
    CategoryDAO categoryDAO = new CategoryDAO(Connection_DB.getInstanceDB());// create a service for this (now using this for testing purpose)

    public void start() throws SQLException {
        System.out.println("Welcome to Expense Tracker Application");
        System.out.println("Login or Signup to continue");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        boolean run = true;
        while (run) {
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
                        // Proceed to the main application logic
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
        System.out.println("9. Exit");

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
                    List<String> categories = categoryDAO.showAllCategories(TransactionType.INCOME);
                    

                    for(int i=0;i<categories.size();i++){
                        System.out.println((i+1)+" - "+categories.get(i));
                    }
                    System.out.println("Select the number of  Income Type :");

                    int selectedCategories = scan.nextInt();
                    
                    incomeService.addIncome(income,selectedCategories);
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
                    System.out.println("Select the category to type incomes: ");
                    categoryDAO.showAllCategories(TransactionType.INCOME).forEach(System.out::println);
                    break;
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
                    run=false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            // // System.out.print("Enter your choice: ");
            // choice = scan.nextInt();
            // scan.nextLine(); // Consume newline character
        }

    }
    public void useExpense() throws SQLException{
        System.out.println("Using Expense Service");
        System.out.println("===================================");
        
        System.out.println("1. Add Income");
        System.out.println("2. View All Incomes");
        System.out.println("3. Get Total Income");
        System.out.println("4. Get Incomes by Month");
        System.out.println("5. Show Incomes by Category");
        System.out.println("6. Update Income");
        System.out.println("7. Temporary delete Income");
        System.out.println("8. Permanently delete Income");
        System.out.println("9. Exit");

        boolean run = true;

        while(run){

            int choice=scan.nextInt();

            switch(choice){
                case 1:

            }
        }


    }
    
    
}
