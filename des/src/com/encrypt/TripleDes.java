package com.encrypt;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDes {
	
	private final static String suk = "A8DD63A3B89D54B37CA802473FDA9175";
	// ISO/IEC 7816-4 padding tag
    private static final byte PAD = (byte) 0x80;
    
	/**
	   * Method to Application Cryptogram
	   * 
	   */
	public static void cryptogram(String message) {
		
		byte[] data = message.getBytes();
		// creating array multiple of 8 bytes
		data = multipleOfEight(data, 8);
		byte[] encryptValue = "0000000000000000".getBytes();
		
		try {	// CBC mode encryption
				// divide byte array into 8 byte blocks
				List<byte[]> list = blocks(data);
				List<byte[]> cipherText = new ArrayList<byte[]>();
				
				for (byte[] bs : list) {
					System.out.println("single 8 byte array :"+Arrays.toString(bs));
					data = xorOperation(bs, encryptValue);
					encryptValue = TripleDes.encrypt(data);
					cipherText.add(encryptValue);
					System.out.println("encryptValue"+encryptValue);
				}
				
				// CBC mode decryption
				byte[] previousCipherText = "0000000000000000".getBytes();
				StringBuilder decryptPlainText = new StringBuilder();
				
				for (int i = 0; i < cipherText.size(); i++) {
					byte[] cipherByte = cipherText.get(i);
					byte[] decryptCipherText=TripleDes.decrypt(cipherByte);
					if(i >= 1) {
						previousCipherText = cipherText.get(i-1);
					}
					byte[] plainText = TripleDes.xorOperation(decryptCipherText, previousCipherText);
					plainText = removePadding(plainText);
					decryptPlainText.append(new String(plainText, "UTF-8"));
					}
				System.out.println("Decrypt value = " + decryptPlainText);
								
				} catch (Exception e) {
				System.err.println("ERROR:: cryptogram:: cryptogram method" + e);
			}
		}
	
	/**
	  * Padding description from ISO/IEC 7816-4; 
	  * relying on ISO9797-1 (here method 2) 
	  *   
	  *  @param byte array
	  *  @param int block size  
	  *  @return padded data 
	  */ 
	private static byte[] multipleOfEight(byte[] data, int blockSize) {
	
    	byte[] result = new byte[data.length + (blockSize - data.length % blockSize)];
    	System.arraycopy(data, 0, result, 0, data.length);
    	result[data.length] = PAD;
    	System.out.println(Arrays.toString(result));
    	
    	return result;
    }
    
    /**
     * creating block.
     *
     * @param byte array multiple of 8 byte
     * @return 8 byte block list
     */
 	private static List<byte[]> blocks(byte[] arr) {
 		
 		List<byte[]> list = new ArrayList<>();
 		int i = 0;
		while ( i < arr.length) {
			int j = 0;
			byte[] des = new byte[8];
			while(j < 8) {
				des[j++] = arr[i++];
			}
			list.add(des);
		}
		
		return list;
 	}
 	
 	
	/**
	   * Method for encryption
	   * 
	   * @param 8 byte byte array
	   * @return ciphertext
	   */
	public static byte[] encrypt(byte[] plainTextBytes) throws Exception {
		
        final MessageDigest md = MessageDigest.getInstance("md5");
        final byte[] digestOfPassword = md.digest(suk.getBytes("utf-8"));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        final byte[] cipherText = cipher.doFinal(plainTextBytes);
        // final String encodedCipherText = new sun.misc.BASE64Encoder()
        // .encode(cipherText);
        
        return cipherText;
    }
	
	/**
	   * Method for decryption
	   * @param 8 byte byte array
	   * @return byte array of plain text
	   */
	public static byte[] decrypt(byte[] message) throws Exception {
		  
        final MessageDigest md = MessageDigest.getInstance("md5");
        final byte[] digestOfPassword = md.digest(suk.getBytes("utf-8"));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, iv);

        // final byte[] encData = new
        // sun.misc.BASE64Decoder().decodeBuffer(message);
        final byte[] plainText = decipher.doFinal(message);

        return plainText;
	    }
			
	/**
	   * Method for xorOperation
	   * @param two byte array
	   * @return byte array
	   */
	private static byte[] xorOperation(byte[] x, byte[] h) {
	        
		byte[] out = new byte[x.length];
        for (int i = 0; i < x.length; i++) {
        	 out[i] = (byte) ((int)x[i] ^ (int)h[i]);
        }
        
        return out;
        }
	
	 /**
	  * Remove padding (relying on ISO 9797-1 method 2) 
	  * @param data 
	  * @return non-padded data 
	  */ 
	public static byte[] removePadding(byte[] data) { 
	
		byte[] rd = null; 
		int i = data.length - 1; 
		while (data[i] == (byte) 0x00) { 
			i--;
			} 
	 
		if (data[i] == (byte) 0x80) { 
			rd = new byte[i]; 
			System.arraycopy(data, 0, rd, 0, rd.length); 
			return rd; 
			} 
		
	  return data; 
	 } 
	}
