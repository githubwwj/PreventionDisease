package com.prevention.disease.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.prevention.disease.R;

public class Utils {

	public static ConcurrentHashMap<String, Bitmap> imageBuffer = new ConcurrentHashMap<String, Bitmap>();
	public static int clickInternal = 900;
	private static long lastTime = 0;
	private static final boolean USING_CUSTOMER = false;
	private static final int CUSTOMER_DURATION = 3000;

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
	
	/**
	 * @param context
	 * @param s
	 *            ��Ϣ
	 */
	public static void showMessage(Context context, String s) {
		ToastUtil.showToast(context, s);
	}

	/**
	 * @param context
	 * @param strId
	 *            ��Ϣid
	 */
	public static void showMessage(Context context, int strId) {
		ToastUtil.showToast(context, strId);
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	private static class ToastUtil {
		private static String mOldMsg;
		private static Toast mToast = null;
		private static long mOneTime = 0;
		private static long mTwoTime = 0;
		private static Context mContext = null;
		private static TextView mTextView;

		public static void showToast(Context context, String s) {
			if (mContext != context) {
				mContext = context;
				mToast = null;
			}

			if (mToast == null) {
				if (USING_CUSTOMER) {
					View view = LayoutInflater.from(mContext).inflate(
							R.layout.toast_view, null);
					mTextView = (TextView) view.findViewById(R.id.t_message);
					mTextView.setText(s);
					mToast = new Toast(mContext);
					mToast.setGravity(Gravity.CENTER, 0, 0);
					mToast.setDuration(CUSTOMER_DURATION);
					mToast.setView(view);
				} else {
					mToast = Toast.makeText(context, s, Toast.LENGTH_LONG);
				}
				mToast.show();
				mOneTime = System.currentTimeMillis();
			} else {
				mTwoTime = System.currentTimeMillis();
				if (s.equals(mOldMsg)) {
					if (mTwoTime - mOneTime > (USING_CUSTOMER ? CUSTOMER_DURATION
							: Toast.LENGTH_LONG)) {
						mToast.show();
						mOneTime = mTwoTime;
					}
				} else {
					mOldMsg = s;
					if (USING_CUSTOMER) {
						mTextView.setText(s);
					} else {
						mToast.setText(s);
					}
					mToast.show();
					mOneTime = mTwoTime;
				}
			}

		}

		public static void showToast(Context context, int resId) {
			showToast(context, context.getString(resId));
		}
	}

	@SuppressLint("SimpleDateFormat")
	public static String timeDiff(Context context, String dateStart,
			String dateStop) {
		// dateStart = "2014-5-14 12:30";
		// dateStop = "2014-5-19 12:32";
		String publishDiffTime = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			// long diffSeconds = diff / 1000 % 60;
			Long minute = diff / (60 * 1000) % 60;
			Long hour = diff / (60 * 60 * 1000) % 24;
			Long day = diff / (24 * 60 * 60 * 1000) % 30;
			Long month = diff / (24 * 60 * 60 * 1000) / 30;

			if (month != null && month > 0) {
				String monthStr = (month == 1) ? context
						.getString(R.string.month_ago) : context
						.getString(R.string.months_ago);
				publishDiffTime += month + " " + monthStr;
			} else if (day != null && day > 0) {
				String dayStr = (day == 1) ? context
						.getString(R.string.day_ago) : context
						.getString(R.string.days_ago);
				publishDiffTime += day + " " + dayStr;
			} else if (hour != null && hour > 0) {
				String hourStr = (hour == 1) ? context
						.getString(R.string.hour_ago) : context
						.getString(R.string.hours_ago);
				publishDiffTime += hour + " " + hourStr;
			} else {
				if (minute != null) {
					String minuteStr = (minute == 1) ? context
							.getString(R.string.minute_ago) : context
							.getString(R.string.minutes_ago);
					publishDiffTime += minute + " " + minuteStr;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publishDiffTime;

	}

	public static void saveMyBitmap(String bitName, Bitmap mBitmap) {
		File f = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + bitName + ".png");
		try {
			if (f.exists()) {
				f.delete();
			} else {
				f.createNewFile();
			}
		} catch (IOException e) {
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap childIcon(Bitmap bt) {
		Bitmap bm = null;
		if (bt != null) {
			bm = convertGreyImg(bt);
			bm = toRoundBitmap(bm);
		}
		return bm;
	}

	/**
	 * 将彩色图转换为灰度图
	 * 
	 * @param img
	 *            位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	/**
	 * 转换图片成圆�?
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public static Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图�?
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图�?
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布�?��件XML看不到效�?
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int[] getScreenWidthHeight(Context context) {
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); 
    	DisplayMetrics dm = new DisplayMetrics();
    	wm.getDefaultDisplay().getMetrics(dm);
    	int widthHeight[]=new int[2];
		widthHeight[0]=dm.widthPixels;
		widthHeight[1]=dm.heightPixels;
        return widthHeight;
    }
    
	/**
	 * 点击间隔
	 * 
	 * @return
	 */
	public static boolean clickInterval() {
		long currentTime = System.currentTimeMillis();
		// Toast.makeText(this,
		// currentTime-lastTime+"time",Toast.LENGTH_SHORT).show();
		if ((currentTime - lastTime) < clickInternal)
			return false;
		lastTime = currentTime;
		return true;
	}
}