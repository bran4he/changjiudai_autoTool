package com.changjiudai.trans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class TransUtil {

	private static Logger logger = Logger.getLogger(TransUtil.class);
	

	public static String getFileFirstLine(String fileName) throws IOException {
		TransUtil u = new TransUtil();
		ClassLoader classLoader = u.getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		FileReader fr = new FileReader(file);
		return new BufferedReader(fr).readLine();
	}

	public static void main(String[] args) throws IOException {
		logger.info(TransUtil.getFileFirstLine("cookie.txt"));
	}
}
