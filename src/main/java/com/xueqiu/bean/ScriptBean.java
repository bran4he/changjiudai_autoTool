package com.xueqiu.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScriptBean {
	private Statuses[] statuses;

	public Statuses[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Statuses[] statuses) {
		this.statuses = statuses;
	}
	
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Statuses{
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class User {
	private String profile_image_url;

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}
	
	/*
	profile_image_url : 
		"community/201511/1449973011158-1449973011792.jpg,
		community/201511/1449973011158-1449973011792.jpg!180x180.png,
		community/201511/1449973011158-1449973011792.jpg!50x50.png,
		community/201511/1449973011158-1449973011792.jpg!30x30.png"
	*/
}