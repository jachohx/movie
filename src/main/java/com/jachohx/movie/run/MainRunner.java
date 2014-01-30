package com.jachohx.movie.run;

public class MainRunner {
	public static void main(String[] args) throws Exception {
    	IRunner runner = null;
    	String runClass = "publichd";
    	if (args.length > 0) {
    		runClass = args[0];
    	}
    	if ("douban_movie".equals(runClass)) {
    		runner = new DoubanMovieRunner();
    	} else if ("publichd_douban".equals(runClass)) {
    		runner = new PublicHDDoubanRunner();
    	} else {
    		runner = new PublicHDRunner();
    	}
    	runner.run();
	}
}
