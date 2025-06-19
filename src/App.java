import java.sql.SQLException;

public class App {
    /*
    *javac -d bin -cp "lib/*" src/App.java src/TrackerApp.java src/database/Connection_DB.java src/database/dao/*.java src/database/model/*.java src/database/passwordManager/PasswordHasher.java src/database/service/*.java 
    * java -cp "bin;lib/*" App
     */
    public static void main(String[] args) throws Exception {
        TrackerApp trackerApp = new TrackerApp();
        try{
            trackerApp.start();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
