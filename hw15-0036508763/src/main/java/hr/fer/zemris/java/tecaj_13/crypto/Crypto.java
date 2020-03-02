package hr.fer.zemris.java.tecaj_13.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import static hr.fer.zemris.java.tecaj_13.crypto.Util.bytetohex;
import static hr.fer.zemris.java.tecaj_13.crypto.Util.hextobyte;

/**
 * Crypto is used to check sha key for a given file,
 * encrypt a file with 32 hex-digits used as a key and another 32 hex-digits as a vector,
 * and finally decrypt a file encrypted by using aforementioned encrypt algorithm.
 * <p>
 * User enters the name of an algorithm which is to be executed.
 * Supported algorithms are:
 * <p>
 * checksha - expects one argument - a path to a file which is being checked.
 * Later it expects from user to provide sha key to be checked against newly created key.
 * encrypt  - expects two arguments - first one is a path to a file which is being encrypted.
 * - second one is a path to a file newly created encrypted file.
 * Later expects user to enter 32 hex-digits key and after that 32 hex-digits vector.
 * File will be encrypted with that key.
 * decrypt  - expects two arguments - first one is a path to an encrypted file
 * - second one is a path to a newly created decrypted file.
 * Later expects user to enter 32 hex-digits key and after that 32 hex-digits vector.
 * File will be decrypted with that key.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Crypto {
    /**
     * Instructions suggested using 4kB of buffer but it can be any reasonably small buffer ranging in few kilobytes.
     */
    private static int BUFFER_SIZE = 1024 * 4;

    /**
     * Method which is used to calculate SHA-256 key for a given file.
     * After the key has been calculated it will be checked against the key which user is required to provide.
     * If the generated key is equal to user provided key, an appropriate message will be printed.
     * If the key differ this method will print an appropriate message.
     *
     * @param path Path to a file which is being checked.
     */
    private static void checksha(String path) {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("No such algorithm");
            return;
        }
        String digestOutput;
        Path p = Paths.get(path);
        try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(p))) {
            byte[] buff = new byte[BUFFER_SIZE];
            while (true) {
                int r = is.read(buff);
                if (r < 1) {
                    break;
                }
                sha.update(buff, 0, r);
            }
            Scanner sc = new Scanner(System.in);
            String keyGivenByUser = sc.nextLine();
            digestOutput = bytetohex(sha.digest());
            if (keyGivenByUser.equals(digestOutput)) {
                System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
            } else {
                System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest. Digest was:" + digestOutput);
            }
            sc.close();
        } catch (IOException ex) {
            //
        }
    }

    /**
     * Method used both for encrypting and decrypting files.
     * This method uses SHA-256 key for it's encryption and decryption.
     *
     * @param inputPath  Path to a file which is being processed.
     * @param outputPath Path to a newly generated file.
     * @param encrypt    If true is given this method will encrypt given file, if false is given it will decrypt the given file.
     */
    private static void encryptAndDecrypt(String inputPath, String outputPath, boolean encrypt) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
        String keyText = sc.nextLine();
        System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
        String ivText = sc.nextLine();
        SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException ex) {
            sc.close();
            System.out.println("No such algorithm");
            return;
        } catch (NoSuchPaddingException ex2) {
            sc.close();
            System.out.println("No such padding");
            return;
        }
        try {
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
        } catch (InvalidKeyException ex) {
            System.out.println("Invalid key");
        } catch (InvalidAlgorithmParameterException ex2) {
            System.out.println("Invalid algorithm parameter");
        }
        Path input = Paths.get(inputPath);
        Path output = Paths.get(outputPath);
        try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(input));
             BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(output))) {
            byte[] buff = new byte[BUFFER_SIZE];
            while (true) {
                int r = is.read(buff);
                if ((r < 1) && (!encrypt)) {
                    break;
                }
                if (BUFFER_SIZE != r) {
                    try {
                        os.write(cipher.doFinal(buff, 0, r));
                    } catch (IllegalBlockSizeException ex) {
                        sc.close();
                        System.out.println("Illegal block size");
                    } catch (BadPaddingException ex2) {
                        sc.close();
                        System.out.println("Bad padding");
                    }
                    break;
                }
                os.write(cipher.update(buff, 0, r));
            }
        } catch (IOException ex) {
            System.out.println("Given file could not be opened.");
        } finally {
            sc.close();
        }
    }

    /**
     * Encrypts given message with SHA-1 algorithm
     * @param message Message which will be encrypted
     * @return Returns encrypted message as an array of bytes;
     */
    public static byte[] digestMessage(String message) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {

        }
        md.update(message.getBytes());
        return md.digest();
    }
}
