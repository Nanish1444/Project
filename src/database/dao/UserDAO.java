package database.dao;
import database.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
public class UserDAO {
    private Connection connection = null;

    private static String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public UserDAO(Connection connection) {
        this.connection = connection;
    }   
    
    public void addUser(User user) throws SQLException {
        //Mail check condition to check the mail is valid or not.
        
        Pattern pattern = Pattern.compile(regex);
        System.out.println("Match : "+pattern.matcher(user.getUserEmail()).matches());
        if(!pattern.matcher(user.getUserEmail()).matches()) {

            throw new IllegalArgumentException("Invalid email check again");
        }
        //Password check condition to check the password is valid or not.
        String sql = "INSERT INTO user (email, name, password) VALUES (?, ?, ?)";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, user.getUserEmail());
        prest.setString(2, user.getUserName());
        prest.setString(3, user.getUserPassword());
        if(prest.executeUpdate()>0){
            System.out.println("User added successfully");
        } else {
            throw new SQLException("Failed to add user");
        }
    }
    public void editUser(String userEmail, String userName, String userPassword) throws SQLException {
        //Mail check condition to check the mail is valid or not.
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(userEmail).matches()) {
            throw new IllegalArgumentException("Invalid email check again");
        }
        //Password check condition to check the password is valid or not.
        String sql = "UPDATE user SET name = ?, password = ? WHERE email = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, userName);
        prest.setString(2, userPassword);
        prest.setString(3, userEmail);
        if(prest.executeUpdate()>0){
            System.out.println("User updated successfully");
        } else {
            throw new SQLException("Failed to update user");
        }
    }
    public void deleteUser(String userEmail) throws SQLException {
        String sql = "DELETE FROM user WHERE email = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, userEmail);
        if(prest.executeUpdate()>0){
            System.out.println("User deleted successfully");
        } else {
            throw new SQLException("Failed to delete user");
        }
    }
    
    public boolean passwordMatches(String userEmail, String userPassword) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND password = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, userEmail);
        prest.setString(2, userPassword);
        
        return prest.executeQuery().next();
    }
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
        prest.setString(1, email);
        // prest.executeQuery().next();
        ResultSet rs = prest.executeQuery();
        
        // rs.next();
        boolean res =rs.next();

        System.out.println("Email exists: " + res);
        return res;
    }
    public String getPassword(String userEmail) throws SQLException {
        String sql = "SELECT password FROM user WHERE email = ?";
        
        PreparedStatement prest = connection.prepareStatement(sql);
         prest.setString(1, userEmail);
         ResultSet rs = prest.executeQuery();
        if(rs == null) {
            throw new SQLException("Failed to retrieve password");
        }
        if (rs.next()) {
            return rs.getString("password").toString();
        } else {
            throw new SQLException("User not found");
        }
    }
}
