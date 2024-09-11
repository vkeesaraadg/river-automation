package com.anikasystems.common.support;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.anikasystems.common.reports.ConfigFileReadWrite;
import com.anikasystems.common.reports.ReporterConstants;

public class EncryptData {
	private static final Logger LOG = Logger.getLogger(EncryptData.class);

	 /**
     * This method encrypts the given clear text password and returns the encrypted password 
	 * 
	 * @param password - DB password which will be encrypted
	 * @throws Exception
	 * @return String - Encrypted password
	 */
	public String EncryptDataValue(String password) throws Exception {
		String encryptedPassword = null;
		String ENCRYPT_DECRYPT_KEY = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "encryptdecryptkey");
        try {
        	//Encrypting password
        	StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        	   	
    		//Load Encrypt_Decrypt_Key from properties file
        	// This is a required password_key for encrypting/decrypting password. Same password_key is used while decrypting the password
    		encryptor.setPassword(ENCRYPT_DECRYPT_KEY);
        	encryptedPassword = encryptor.encrypt(password);
        
        } catch (Exception e) {
            throw new Exception("Problem encountered during encryption process",e);
        }
		return encryptedPassword;
     }	
}
