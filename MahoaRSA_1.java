import java.io.File;
//import java.io.FileInputStream;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MahoaRSA_1 {
    private static String encrypted;
    private static String decrypted;
    private static Scanner original;
    // Gọi lại 2 cặp khóa để sử dụng 
    public static PrivateKey getPrivateKey() throws Exception, FileNotFoundException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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

    public static void main(String[] args) throws IOException {
        File file = null;
        FileReader reader = null;
        FileWriter fw = new FileWriter("textOut.txt");

        file = new File("text1.txt");
        original = new Scanner(file);

        String text = original.nextLine();
        
        reader = new FileReader(file);
        int i;

        try {
            PrivateKey privateKey = getPrivateKey();
            PublicKey publicKey = getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA");
            // Mã hóa file có độ dài trên 245bit
            if(file.length() > 245){
                long start = java.util.Calendar.getInstance().getTimeInMillis();                
                while ((i = reader.read()) != -1) {
                    char t = (char) i;
                    String temp = Character.toString(t);                    
                    
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);                    
                    byte[] byteEncrypted = cipher.doFinal(temp.getBytes());
                    encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);                
                    
                    cipher.init(Cipher.DECRYPT_MODE, privateKey);
                    byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
                    decrypted = new String(byteDecrypted);
    
                    fw.write(decrypted);
                }
                long end = java.util.Calendar.getInstance().getTimeInMillis();
                System.out.println("Speed: " + (end - start) + "ms");
            }
            // Mã hóa file có độ dài dưới 245bit
            else {
                long start = java.util.Calendar.getInstance().getTimeInMillis();

                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] byteEncrypted = cipher.doFinal(text.getBytes());
                encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);                
                
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
                decrypted = new String(byteDecrypted);

                fw.write(decrypted);
                long end = java.util.Calendar.getInstance().getTimeInMillis();
                System.out.println("Speed: " + (end - start) + "ms");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        fw.close();        
    }
}
