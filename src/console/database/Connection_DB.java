package console.database;
import java.sql.Connection;
import java.sql.DriverManager;

public class Connection_DB {
    private final static String url="jdbc:mysql://localhost:3306/code";
    private final static String user="root";
    private final static String pass="1234";

    static Connection instance = null;

    public static Connection getInstanceDB() {
        if(instance == null){
            try{
                instance = DriverManager.getConnection(url, user, pass);
            }
            catch(Exception e){
                e.printStackTrace();   
                System.out.println("Error in connection: " + e.getMessage());
                // System.exit(0);
            }
            return instance; 
        }
        return instance;
    }

    
}
