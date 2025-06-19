package database.service;

import java.sql.SQLException;

import database.Connection_DB;

import database.dao.UserDAO;
import database.model.User;
import database.passwordManager.PasswordHasher;


public class UserService {

    UserDAO userDAO=new UserDAO(Connection_DB.getInstanceDB());

    public boolean signup(String email,String username, String password) throws SQLException {
        String hashPass=PasswordHasher.hashPassword(password);
        if(email == null || email.isEmpty() || username == null || username.isEmpty() || password == null || password.isEmpty()) {
           System.out.println("Email, username, and password cannot be empty.");
           return false; // Invalid input
        }

        //Check the same user exists or not
        if (userDAO.emailExists(email)) {
            System.out.println("Email already exists. Please use a different email.");
            return false; // User already exists
        }
        else{
            User user = new User(email ,username, hashPass);
            try {
                userDAO.addUser(user);
            } catch (SQLException e) {
                System.out.println("Error while registering user: " + e.getMessage());
                return false; // Registration failed
            }
        }
        return true;
        // Logic to register a new user
    }

    public boolean login(String userMail, String password) {
        String hashPass=PasswordHasher.hashPassword(password);
        try {
            if(userDAO.emailExists(userMail)){
                if(PasswordHasher.verifyPassword(hashPass, userDAO.getPassword(userMail))){
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid password. Please try again.");
                    return false; // Login failed
                }
            } 
            else {
                System.out.println("You don't have an account. Please sign up first.");
                return false; // Login failed
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }



}
