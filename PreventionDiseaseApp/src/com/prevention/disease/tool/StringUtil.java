package com.prevention.disease.tool;

import java.text.DecimalFormat;

public class StringUtil {

	public static String notNull(String obj) {
		if (obj != null && !"".equals(obj.trim()) && !"null".equals(obj.trim())) {
			return obj;
		} else {
			return "";
		}
	}

	public static Boolean notNull(Object obj) {
		if (obj != null && !"".equals(obj.toString())
				&& !"null".equals(obj.toString().trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @user : wwj
	 * @date : 2014-2-19
	 * @param obj
	 * return  true为空    false不为�?
	 */
	public static Boolean isEmpty(Object obj) {
		if (obj != null && !"".equals(obj.toString())
				&& !"null".equals(obj.toString().trim())) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotBlank(String o) {
		if (o != null && !"".equals(o.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static String SicenToComm(double value, int com) { 

		String retValue = null;

		DecimalFormat df = new DecimalFormat();

		df.setMinimumFractionDigits(com);

		df.setMaximumFractionDigits(com);

		retValue = df.format(value);

		retValue = retValue.replaceAll(",", "");

		return retValue;

	}


	public static double lngToPixel(double lng, int zoom) {

		return (lng + 180) * (256L << zoom) / 360;

	}


	public static double pixelToLng(double pixelX, int zoom) {

		return pixelX * 360 / (256L << zoom) - 180;

	}


	public static double latToPixel(double lat, int zoom) {

		double siny = Math.sin(lat * Math.PI / 180);

		double y = Math.log((1 + siny) / (1 - siny));

		return (128 << zoom) * (1 - y / (2 * Math.PI));

	}


	public static double pixelToLat(double pixelY, int zoom) {

		double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));

		double z = Math.pow(Math.E, y);

		double siny = (z - 1) / (z + 1);

		return Math.asin(siny) * 180 / Math.PI;

	}

	public static String subStringByLength(String s, Integer i) {
		String str = "";
		if (StringUtil.isNotBlank(s) && i != null) {
			if (s.length() > i) {
				str = s.substring(0, i) + "...";
			} else {
				str = s;
			}
		}
		return str;
	}

	public static String str2Utf8(String str) {
		try {
			byte[] buf = str.getBytes("ISO-8859-1");
			return new String(buf, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
}
