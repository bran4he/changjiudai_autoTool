package com.changjiudai.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class PropertiesUtil {

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	private static Properties props = new Properties();
	
	static {
		Properties properties = new Properties();
		InputStream ins = PropertiesUtil.class.getResourceAsStream("/prop.properties");
		try {
			properties.load(ins);
			
			Set<Object> keySet = properties.keySet();
			for (Object object : keySet) {
				String value = properties.getProperty(object.toString());
				
				logger.info("load from origin prop.properties, " + object.toString() + ":" + value);
				
				if("Y".equalsIgnoreCase(value)){
					InputStream insTemp = PropertiesUtil.class.getResourceAsStream("/" + object.toString() + ".properties");
					if(null != insTemp){
						props.load(insTemp);
					}
				}
			}
			logger.info(props.toString());
			
		} catch (IOException e) {
			logger.info("load prop.properties file stream error");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PropertiesUtil.getValue("christina");
		PropertiesUtil.getValue("christina.server");
		PropertiesUtil.getValue("exportedPages");
	}

	public static String getValue(String key, String fileName) {
		Properties props = new Properties();
		try {
			InputStream ins = PropertiesUtil.class.getResourceAsStream(fileName);
			props.load(ins);
			String value = props.getProperty(key);
			logger.info(key + " :key's value is：" + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, String> getAll(){
		HashMap<String, String> map = new HashMap<String, String>();
		Set<Object> keySet = props.keySet();
		for (Object object : keySet) {
			map.put(object.toString(), props.getProperty(object.toString()));
		}
		return map;
	}
	
	public static String getValue(String key) {
			String value = props.getProperty(key);
			logger.info(key + ":key's value is：" + value);
			return value;
	}

//	public static void write(String filePath, String key, String value) throws FileNotFoundException {
//		Properties prop = new Properties();
//		File file = new File(PropertiesUtil.class.getClassLoader().getResource("").getPath() + filePath);
//		try {
//			logger.info("file path：" + file.getCanonicalPath());
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// InputStream ins = new FileInputStream(file);
//		InputStream ins = PropertiesUtil.class.getResourceAsStream("/" + filePath);
//		try {
//			prop.load(ins);
//
//			OutputStream out = new FileOutputStream(file);
//			prop.setProperty(key, value);
//			prop.store(out, "update");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
