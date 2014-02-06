package com.jachohx.movie.run;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.jachohx.movie.util.SpringUtils;

public class MainRunner {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Map<String, IRunner> runnersMap = (Map<String, IRunner>)SpringUtils.getInstance().getBean("runnersMap");
    	IRunner runner = null;
    	String runClass = "main";
    	if (args.length > 0) {
    		runClass = args[0];
    	}
    	if ((runner = runnersMap.get(runClass)) == null) {
    		throw new Exception("no [" + runClass + "] Runner Class");
    	}
    	if (args.length > 1) {
    		args = ArrayUtils.subarray(args, 1, args.length);
    	} else args = new String[]{};
    	runner.run(args);
	}
}
