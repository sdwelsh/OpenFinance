/**
 * 
 */
package data.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

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
	
	
	FileEncrypterDecrypter(String secretKey, String transformation) {
		
		byte[] keyBytes = new byte[16];
		
		byte[] oldBytes = secretKey.getBytes();
		
		for(int i = 0; i < keyBytes.length; i++) {
			keyBytes[i] = oldBytes[i];
		}
		
	    this.secretKey = new SecretKeySpec(keyBytes, "AES");
	    try {
			this.cipher = Cipher.getInstance(transformation);
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchElementException();
		} catch (NoSuchPaddingException e) {
			throw new NoSuchElementException();
		}
	}
	
	
	void encrypt(String content, String fileName) {
	    try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e1) {
			throw new NoSuchElementException();
		}
	    byte[] iv = cipher.getIV();
	 
	    try (FileOutputStream fileOut = new FileOutputStream(fileName);
	      CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
	        fileOut.write(iv);
	        cipherOut.write(content.getBytes());
	    } catch (FileNotFoundException e) {
	    	File file = new File(fileName);
	    	try {
	    		file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e1) {
				throw new NoSuchElementException();
			}
		} catch (IOException e) {
			throw new NoSuchElementException();
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
			throw new NoSuchElementException();
		} catch (IOException e1) {
			throw new NoSuchElementException();
		}
	    return content;
	}
	
	
}
