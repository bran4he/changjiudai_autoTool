package com.xueqiu.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bean2 {

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Bean2(int count) {
		super();
		this.count = count;
	}

	public Bean2() {
		super();
		// TODO Auto-generated constructor stub
	}
}
