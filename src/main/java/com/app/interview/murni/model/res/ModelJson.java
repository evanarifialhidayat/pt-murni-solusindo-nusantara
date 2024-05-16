package com.app.interview.murni.model.res;

public class ModelJson<T> {
	
	private Boolean result;
	private String errors;
	private T list;
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public T getList() {
		return list;
	}
	public void setList(T list) {
		this.list = list;
	}
	
	
	

}
