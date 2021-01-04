import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.Scanner;

public class MahoaRSA {
    public static PrivateKey getPrivateKey() throws Exception, FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyBytes = Files.readAllBytes(new File(TaoKeyRSA.PRIVATE_KEY_FILE).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
    public static PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(TaoKeyRSA.PUBLIC_KEY_FILE).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
    
    private static Scanner original;

    public static void main(String[] args) throws Exception {
        long start = java.util.Calendar.getInstance().getTimeInMillis();
        PrivateKey privateKey = getPrivateKey();
        PublicKey publicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        String url = "text1.txt";
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        original = new Scanner(fileInputStream);
        
        String text = original.nextLine();

        
        byte[] byteEncrypted = cipher.doFinal(text.getBytes());
        String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
        
        
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
        String decrypted = new String(byteDecrypted);

        System.out.println(" ");
        System.out.println("Original  text: " + text + "\n");
        System.out.println("Encrypted text: " + encrypted + "\n");
        System.out.println("Decrypted text: " + decrypted + "\n");

        long end = java.util.Calendar.getInstance().getTimeInMillis();
        System.out.println("Speed: " + (end - start) + "ms");
    }
    
}
