package com.jachohx.movie.entity;

public class MovieInfo {
	private String name;
	private int year;
	public MovieInfo(){
	}
	public MovieInfo(String name, int year){
		this.name = name;
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
