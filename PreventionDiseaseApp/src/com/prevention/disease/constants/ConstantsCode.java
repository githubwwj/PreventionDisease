
package com.prevention.disease.constants;

import android.os.Environment;


/**
 * Global constants.
 */
public class ConstantsCode {

    public final static String PKG_NAME = "com.mengqi.diibear.teacher";

    // Global message ids, starting from 101.
    public static final int MSG_STORE_DOWNLOAD_COMPLETE = 101;
    public static final int MSG_NO_SDCARD = 102;
    public static final int MSG_AD_DOWNLOAD_COMPLETE = 103;
    public static final int MSG_INTERNAL_STORAGE_SPACE_NOT_ENOUGH = 104;
    public static final int MSG_FREE_SPACE_NOT_ENOUGH_TO_SAVE_FILE = 105;
    public static final int MSG_SDCARD_FREE_SPACE_TOO_LOW = 106;
    
    public static final String SYS_DATA_DIR = Environment.getDataDirectory().getAbsolutePath();
    public final static String DATA_BASE_PATH = SYS_DATA_DIR + "/data/" + PKG_NAME + "/databases/";
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Taidii/";
    public final static String IMAGE_BUCKET_NAME = "/sdcard/Taidii";
    
 // Empty string definition
    public final static String EMPTY_STRING = "";
    
    public final static int ERR_OK = 0;
    public final static int ERR_NETWORK_TIMEOUT = 9997;
    public final static int ERR_NETWORK_EXCEPTION = 9998;
    public final static int ERR_UNKNOWN = 9999;
    public final static int ERR_SERVER_PROBLEM = 10000;
    public final static int ERR_BAD_PARAS = 10001;
    
    public final static int CONN_DEFAULT_TIMEOUT = 30*1000;
    public final static int SOCK_DEFAULT_TIMEOUT = 30*1000;
    
    //preference
    public static final String DIIBEAR_PROPERTIES = "Diibear_Properties";
    public static final String PROPERTIES_USERNAME = "Properties_Username";
    public static final String PROPERTIES_PASSWORD = "Properties_password";
    public static final String PROPERTIES_TOKEN = "Properties_token";
    
    //Bundle
    public final static String BUNDLE_ADDRESS_NAME = "address";
    public final static String BUNDLE_CALLERY_POSITION = "position";
    public final static String BUNDLE_CALLERY_PHOTOS = "photos";
    public final static String BUNDLE_ALBUM_ID = "albumId";
    public final static String BUNDLE_MOMENT = "moment";
    public final static String BUNDLE_PHOTO_URLS = "photoUrils";
    
 // Message base definitions
    public final static int MSG_BASE_OFFSET = 16;
    
    public final static int MSG_BASE_ACTIVITY_BASE = 0x100 << MSG_BASE_OFFSET;
    public final static int MSG_ACTIVITY_LOGIN = 0x101 << MSG_BASE_OFFSET;
    public final static int MSG_ACTIVITY_COLUMN = 0x102 << MSG_BASE_OFFSET;
    public final static int MSG_ACTIVITY_ITEM = 0x103 << MSG_BASE_OFFSET;
    public final static int MSG_ACTIVITY_ATTACH = 0x104 << MSG_BASE_OFFSET;
    
    public final static String ATTACH_TEXTVIEW ="99990";
    public final static String ATTACH_EXPENDLIST = "99991";
    public final static String TOKEN_STRING ="Token ";
    
    public final static int MAIN_ACTIVITY_RESULT= 15;
    public final static String TYPE_USER_PRINCIPAL = "Principal";
    public final static String TYPE_USER_TEACHER = "Teacher";
    public final static String TYPE_ALBUM_UNSORTED = "Unsorted";
    public final static int TAKE_PHOTO_RESULT= MAIN_ACTIVITY_RESULT+1;
    public final static int CHOOSE_ALBUM= TAKE_PHOTO_RESULT+1;
    public final static int CHOOSE_STUDENT= CHOOSE_ALBUM+1;
    public final static int CHOOSE_ACTIVITY= CHOOSE_STUDENT+1;
    public final static int LEARN_AREA=CHOOSE_ACTIVITY+1;
}
