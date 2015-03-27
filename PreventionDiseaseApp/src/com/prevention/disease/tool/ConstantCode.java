package com.prevention.disease.tool;

import android.os.Environment;

public class ConstantCode {
	
	public static final String SD_CARD_DIR = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public final static String IMAGE_ROOT_PATH = SD_CARD_DIR + "/Taidii";
    public final static String IMAGE_BUCKET_NAME = "/sdcard/Taidii";
	
	// Message base definitions
    public final static int MSG_BASE_OFFSET = 16;
    public final static int BUNDLE_BASE = 0x100 << MSG_BASE_OFFSET;
    public final static int REQUEST_BASE = 0x101 << MSG_BASE_OFFSET;
    public final static int MSG_BASE = 0x102 << MSG_BASE_OFFSET;
    public final static int MSG_PDATTACH = 0x103 << MSG_BASE_OFFSET;
    
    //PDAttachActivity
    public static final String ATTACH_INTRODUCE = "99990";
    public static final String ATTACH_TITLE = "99991";
    public static final String ATTACH_CONTENT = "99992";
    public static final String ATTACH_OVERRIDE = "99993";
    public static final String ATTACH_LISTVIEW = "99994";
    
	//bundle
	public static final String BUNDLE_CENTER_ID = "centerId";
	public static final String BUNDLE_ABOUT_US_BEAN = "aboutus";
	public static final String BUNDLE_LATITUDE = "latitude";
	public static final String BUNDLE_LONGITUDE = "longitude";
	public static final String BUNDLE_NAME = "name";
	public static final String BUNDLE_NEED_LOCATION = "isNeedLocation";
	public static final String BUNDLE_START_ID = "startid";
	public static final String BUNDLE_END_ID = "endid";
	
	//requst
	public static final int REQUEST_GET_CENTER_ID = REQUEST_BASE+1;
	
	//MSG
	public static final int MSG_GET_CENTER_ID = MSG_BASE+1;
	public static final int MSG_SET_ALIAS = MSG_BASE+2;
	public static final int MSG_NET_ERROR = MSG_BASE+3;
	public static final int MSG_GET_EVENT_SUCCESS = MSG_BASE+4;
	public static final int MSG_VISITOR = MSG_BASE+5;
	
	public static final int GOOGLE_RESULT_TO_VISITATY=900;
	
	public final static int DEFAULT_LOAD_OK = 0;
	public final static int REFRESH_LOAD_OK = 1;
	public final static int LOAD_MORE_LOAD_OK = 2;
	public final static int NET_ERROR = 3;
	public final static int LOAD_ERROR = 4;
	public final static int NOT_MORE_DATA = 5;
	public static final int NO_DATA = 6;
	
	public final static int BACKGROUND_UPDATE_TIME = 5000;
}
