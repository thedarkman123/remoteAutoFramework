package utilities;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LoggerWrapper {	
	 public static ThreadLocal<Logger> loggerThread= 
			 new ThreadLocal<Logger>();	
	 	 
	 public static Logger init(String path,String name) {
		 FileAppender appender = new FileAppender();
		 Logger logger = null;
		 
	     appender.setFile(path);
	     appender.setLayout(new PatternLayout("%d [%t] %-5p %c - %m%n"));
	     appender.activateOptions();
	     
	     logger.getLogger(name);
	     logger.setAdditivity(false);
	     logger.addAppender(appender);
	     return logger;
	 }
	 
	 public static void setLogger(Logger lg) {
		 loggerThread.set(lg);
	 }
	
	public static Logger getLogger() {
		return loggerThread.get();
	}
	
//	 public static void setAppender(FileAppender appender) {
//		 appenderThread.set(appender);
//	 }
//	
//	public static FileAppender getAppender() {
//		return appenderThread.get();
//	}
	  
	 public static void logToFile(String strTofile) {
		 getLogger().info(strTofile);
	 }
    
//	 public static void closeLog() {
//		 // Remove appender
//		 getLogger().removeAppender(getAppender());
//	 }
    
}
