package console.passwordManager;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    //  private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String rawPassword) {
        String salt = rawPassword.hashCode() + "";
        return salt;
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            throw new IllegalArgumentException("Raw password and hashed password cannot be null.");
        }
        return hashedPassword.equals(hashPassword(rawPassword));
    }
}
