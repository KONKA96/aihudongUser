package com.model;

public class Err {
	private int errcode;
	
	private String msg;
	
	public Err() {
		super();
	}

	public Err(int errcode, String msg) {
		super();
		this.errcode = errcode;
		this.msg = msg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
