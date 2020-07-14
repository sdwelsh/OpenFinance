/**
 * 
 */
package data.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Stephen Welsh
 *
 */
public class FileEncrypterDecrypter {
	
	private Cipher cipher;
	
	Key secretKey;
	
	
	FileEncrypterDecrypter(byte[] secretKey, String transformation) {
	    this.secretKey = new SecretKeySpec(secretKey, "AES");
	    try {
			this.cipher = Cipher.getInstance(transformation);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	void encrypt(String content, String fileName) {
	    try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    byte[] iv = cipher.getIV();
	 
	    try (FileOutputStream fileOut = new FileOutputStream(fileName);
	      CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
	        fileOut.write(iv);
	        cipherOut.write(content.getBytes());
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	String decrypt(String fileName) {
	    String content = "";
	 
	    try (FileInputStream fileIn = new FileInputStream(fileName)) {
	        byte[] fileIv = new byte[16];
	        fileIn.read(fileIv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));
	 
	        try (
	                CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
	                InputStreamReader inputReader = new InputStreamReader(cipherIn);
	                BufferedReader reader = new BufferedReader(inputReader)
	            ) {
	 
	            StringBuilder sb = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	            content = sb.toString();
	        }
	 
	    } catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    return content;
	}
	
	
}
