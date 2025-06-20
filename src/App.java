import java.sql.SQLException;

public class App {
    /*
    *javac -d bin -cp "lib/*" src/App.java src/TrackerApp.java src/console/Connection_DB.java src/console/dao/*.java src/console/model/*.java src/console/passwordManager/PasswordHasher.java src/console/service/*.java src/console/database/*.java
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
