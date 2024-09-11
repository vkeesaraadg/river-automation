package com.anikasystems.common.support;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.anikasystems.common.reports.ConfigFileReadWrite;
import com.anikasystems.common.reports.ReporterConstants;

public class DecryptData {
	 /**
     * This method encrypts the given clear text password and returns the encrypted password 
	 * @param password - DB password which will be decrypted
	 * @throws Exception
	 * @return String - plain text password
	 */
	public String DecryptDataValue(String password) throws Exception {
		String decryptedPassword = null;
		String ENCRYPT_DECRYPT_KEY = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "encryptdecryptkey");
        try {
        	//Decrypting password
        	StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        	// This is a Password_key set which was set while encrypting and same key is used while decrypting password
        	encryptor.setPassword(ENCRYPT_DECRYPT_KEY);
        	decryptedPassword = encryptor.decrypt(password);
        
        } catch (Exception e) {
            throw new Exception("Problem encountered during encryption process",e);
        }
		return decryptedPassword;
     }	
}
