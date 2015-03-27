
package com.prevention.disease.constants;

/**
 * Server configuration.
 */
public class ServerInfo {

    /**
     * Distinguish if it is release version or debug version.Trueï¿?release;
     * Falseï¿?debug
     */
    public static final boolean IS_RELEASE = false;

    /**
     * Configure if log is available in a release version.
     */
    public static boolean LOG_ENABLE_ON_RELEASE = true;
    
    /**
     * erorr log saved in sdcard
     */
    public static final boolean LOGOUT = true;


    public static String MD5Key;

//    public static final String SERVICE_NAMESPACE = "http://54.251.98.195";
	public static final String SERVICE_NAMESPACE = "http://54.251.247.11";

    /**
     * Web service URL.
     */
    public static String WEB_SERVICE_URL;

    public static String FILE_SERVER_URL;
}
