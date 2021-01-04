
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MahoaDES {
    private static Scanner original;

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        long start = java.util.Calendar.getInstance().getTimeInMillis();
        String SECRET_KEY = "12345678";
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");

        // Chuỗi cần mã hóa 
        String url = "text1.txt";
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        FileWriter fw = new FileWriter("textOut.txt");
        original = new Scanner(fileInputStream);
        
        String text = original.nextLine();
        
        // Chuỗi sau mã hóa
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] byteEncrypted = cipher.doFinal(text.getBytes());
        String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
        
        // Chuỗi gốc sau khi giải mã
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
        String decrypted = new String(byteDecrypted);        
        
        System.out.println();
        fw.write(decrypted);
        fw.close();
        //System.out.println("Original  text: " + text + "\n");
        //System.out.println("Encrypted text: " + encrypted + "\n");
        //System.out.println("Decrypted text: " + decrypted + "\n");
        
        long end = java.util.Calendar.getInstance().getTimeInMillis();
        System.out.println("Speed: " + (end - start) + "ms");
    }
    
}
