package com.jachohx.movie.run;

public class MainRunner {
	public static void main(String[] args) throws Exception {
    	IRunner runner = new PublicHDRunner();
    	runner.run();
	}
}
