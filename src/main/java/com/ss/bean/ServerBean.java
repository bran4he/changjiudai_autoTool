package com.ss.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerBean {

	private String exist;
	private String server;
	private String port;
	private String encrypt = "aes-256-cfb";
	private String password;

	public static void main(String[] args) {
		ServerBean sb = new ServerBean();
		ObjectMapper mapper = new ObjectMapper();

		String json = null;
		try {
			json = mapper.writeValueAsString(sb);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Java2Json: " + json);
	}

	public ServerBean() {
		super();
	}

	public ServerBean(String exist, String server, String port, String encrypt, String password) {
		super();
		this.exist = exist;
		this.server = server;
		this.port = port;
		this.encrypt = encrypt;
		this.password = password;
	}

	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
