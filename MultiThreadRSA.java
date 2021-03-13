import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
//import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MultiThreadRSA {
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

    private static String decrypted;
    
    public static void main(String[] args) throws IOException, InterruptedException {

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = null;
                file = new File("text2.txt");

                FileReader reader = null;
                try {
                    reader = new FileReader(file);
                    FileWriter fw = new FileWriter("textOut.txt");
                    int i;
                    while ((i = reader.read()) != -1) {
                        char t = (char) i;
                        String temp = Character.toString(t);
                        PrivateKey privateKey = getPrivateKey();
                        PublicKey publicKey = getPublicKey();
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

                        byte[] byteEncrypted = cipher.doFinal(temp.getBytes());
                        Base64.getEncoder().encodeToString(byteEncrypted);

                        cipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
                        decrypted = new String(byteDecrypted);

                        fw.write(decrypted);
                    }
                    fw.close();
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
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = null;
                file = new File("text2.txt");

                FileReader reader = null;
                try {
                    
                    reader = new FileReader(file);
                    FileWriter fw = new FileWriter("textOut.txt");
                    int i;
                    while ((i = reader.read()) != -1) {
                        char t = (char) i;
                        String temp = Character.toString(t);
                        PrivateKey privateKey = getPrivateKey();
                        PublicKey publicKey = getPublicKey();
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

                        byte[] byteEncrypted = cipher.doFinal(temp.getBytes());
                        Base64.getEncoder().encodeToString(byteEncrypted);

                        cipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
                        decrypted = new String(byteDecrypted);

                        fw.write(decrypted);
                    }
                    fw.close();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        long start = java.util.Calendar.getInstance().getTimeInMillis();
        t2.start();
        t2.join();
        t1.start();
        long end = java.util.Calendar.getInstance().getTimeInMillis();
        System.out.println("Speed: " + (end - start) + "ms");
    }
}
