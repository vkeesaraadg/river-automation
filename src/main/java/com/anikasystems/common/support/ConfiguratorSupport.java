package com.anikasystems.common.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Framework level reusable methods to set and get the values from property file based on their keys defined in property file
 * @author CignitiTeam
 *
 */
public class ConfiguratorSupport {
	//public final Logger LOG = Logger.getLogger(ConfiguratorSupport.class);
	static Properties props = new Properties();
	String strFileName;
	String strValue;

	public String getProperty(String strKey) {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				strValue = props.getProperty(strKey);
				in.close();
			} 
		} catch (Exception e) {
			//LOG.info(e);
		}
		return strValue;
	}

	public void setProperty(String strKey, String strValue) throws Throwable {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				props.setProperty(strKey, strValue);
				props.store(new FileOutputStream(strFileName), null);
				in.close();
			} else {
				//LOG.info("File not found!");
			}
		} catch (Exception e) {
			//LOG.info(e);
		}
	}

	public void removeProperty(String strKey) {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				props.remove(strKey);
				props.store(new FileOutputStream(strFileName), null);
				in.close();
			} 
		} catch (Exception e) {
			//LOG.info(e);
		}
	}

	public ConfiguratorSupport(String strFileName) {
		this.strFileName = strFileName;
	}

	// return environmental details
	public static String getHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();

		return hostname;
	}
}
