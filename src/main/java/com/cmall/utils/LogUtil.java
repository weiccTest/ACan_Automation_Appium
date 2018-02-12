package com.cmall.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogUtil {

	private final Class<?> clazz;
	private Logger logger;
	
	public LogUtil(Class<?> clazz) {
		this.clazz = clazz;
		this.logger = LogManager.getLogger(this.clazz);
	}

	public void info(String message) {
		logger.info(message);
	}
	
	public void info(String tag,String message) {
		logger.info(tag + message);
	}

	public void debug(String message) {
		logger.debug(message);
	}

	public void error(String message) {
		logger.error(message);
	}

	public void trace(String message) {
		logger.trace(message);
	}

	public void warn(String message) {
		logger.warn(message);
	}

	public void fatal(String message) {
		logger.fatal(message);
	}
	
}
