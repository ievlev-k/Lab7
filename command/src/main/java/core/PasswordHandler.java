package core;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.NoSuchElementException;

public class PasswordHandler {

    public static String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            String newPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
            return newPassword;
        }catch (NoSuchAlgorithmException e){
            System.out.println("Алгоритм не был найден");
        }
        return null;
    }
}
