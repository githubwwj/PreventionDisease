package com.prevention.disease.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.prevention.disease.R;
import com.prevention.disease.activity.SearchActivity;
import com.prevention.disease.constants.ConstantsCode;
import com.prevention.disease.dialog.MProgressDialog;
import com.prevention.disease.tool.Utils;
import com.prevention.disease.view.DrawerView;

public class BaseFragmentActivity extends FragmentActivity implements OnClickListener, Callback {
	protected ImageView mMenu;
	protected ImageView search;
	protected TextView mTitle;
	protected Toast mToast;
	private MProgressDialog mProgressDialog;
	protected Context mContext;

	protected String mFrom = ConstantsCode.EMPTY_STRING;
	protected Intent mIntent = null;
	private Handler mUiMessageHandler;

	private static final int MSG_DISMISS_PROGRESS_DIALOG = 0;
	private static final int MSG_SHOW_TOAST = 1;
	private static final int MSG_SHOW_PROGRESS_DIALOG = 2;
	protected SlidingMenu side_drawer;
	protected String TAG = "BaseActivity";
	private long mExitTime;

	public String getClassName() {
		String clazzName = getClass().getName();
		return clazzName;
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.search:
				Intent intent=new Intent(mContext,SearchActivity.class);
				startActivityForResult(intent, 100);
				break;
			case R.id.menu:
				if (side_drawer.isMenuShowing()) {
					side_drawer.showContent();
				} else {
					side_drawer.showMenu();
				}
				break;
			default:
				break;
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		analyseIntent();
		side_drawer = new DrawerView(this).initSlidingMenu();
		initWidgetProperty();
		initWidgetEvent();
	}

	protected void analyseIntent() {
	}

	protected boolean checkForms() {
		return true;
	}
	
	protected void setTitle(String title){
		if(null!=mTitle&&null!=title){
			mTitle.setText(title);
		}
	}

	protected void onPause() {
		super.onPause();
	}

	protected void onResume() {
		super.onResume();
	}

	protected void addWidgetEventListener(View v) {
		if (v != null)
			v.setOnClickListener(this);
	}

	protected void doWithoutNetwork() {
	}

	protected void dismissProgressBar() {
		Message message = Message.obtain();
		message.what = MSG_DISMISS_PROGRESS_DIALOG;
		mBaseHandler.sendMessage(message);
	}

	protected void initWidgetEvent() {
		addWidgetEventListener(mMenu);
		addWidgetEventListener(search);
	}

	protected void initWidgetProperty() {
		mMenu = (ImageView) findViewById(R.id.menu);
		search = (ImageView) findViewById(R.id.search);
		mTitle = (TextView) findViewById(R.id.title);
		mUiMessageHandler = new Handler(Looper.getMainLooper(), this);
	}

	protected void setSingleValueOfReceiptDetail(int componentId, int stringId,
			Object value, int color) {
		TextView receiptTitle = (TextView) findViewById(componentId);
		String text4 = String.format(getResources().getString(stringId), value);
		int index;
		index = text4.indexOf(value.toString());

		SpannableStringBuilder style2 = new SpannableStringBuilder(text4);
		style2.setSpan(new ForegroundColorSpan(color), index, index
				+ value.toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		receiptTitle.setText(style2);

	}

	protected void showProgressDialog(String title) {
		Message message = Message.obtain();
		message.what = MSG_SHOW_PROGRESS_DIALOG;
		message.obj = title;
		mBaseHandler.sendMessage(message);
	}

	protected void showProgressDialog() {
		showProgressDialog("");
	}

	protected void showToast(int resourceId) {
		showToast(getString(resourceId));
	}

	protected void showToast(String text) {
		Message message = Message.obtain();
		message.what = MSG_SHOW_TOAST;
		message.obj = text;
		mBaseHandler.sendMessage(message);
	}

	/**
	 * General method for starting a new activity either for result or not.
	 * 
	 * @param activityClass
	 *            The activity to start
	 * @param bundle
	 *            Extra information with this intent.
	 * @param isReturn
	 *            If start for result or not
	 * @param requestCode
	 *            The request code.
	 * @param isFinish
	 *            If finish self after start.
	 */
	public void jumpToPage(Class<?> activityClass, Bundle bundle,
			boolean isReturn, int requestCode, boolean isFinish) {
		if (activityClass == null) {
			return;
		}

		Intent intent = new Intent();
		intent.setClass(this, activityClass);

		if (bundle != null) {
			intent.putExtras(bundle);
		}

		if (isReturn) {
			startActivityForResult(intent, requestCode);
		} else {
			startActivity(intent);
		}

		if (isFinish) {
			finish();
		}
	}

	public void jumpToPage(Class<?> activityClass) {
		jumpToPage(activityClass, null, false, 0, false);
	}

	public void jumpToPage(Class<?> activityClass, boolean isFinish) {
		jumpToPage(activityClass, null, false, 0, isFinish);
	}

	public void jumpToPage(Class<?> activityClass, Bundle bundle) {
		jumpToPage(activityClass, bundle, false, 0, false);
	}

	public void jumpToPage(Class<?> activityClass, Bundle bundle,
			boolean isFinish) {
		jumpToPage(activityClass, bundle, false, 0, isFinish);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mProgressDialog != null) {
				mBaseHandler.sendEmptyMessage(MSG_DISMISS_PROGRESS_DIALOG);
			}
			if (side_drawer.isMenuShowing()
					|| side_drawer.isSecondaryMenuShowing()) {
				side_drawer.showContent();
			} else {
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(
							this,
							getResources().getString(R.string.click_twice_exit),
							Toast.LENGTH_SHORT).show();
					mExitTime = System.currentTimeMillis();
				} else {
					finish();
				}
			}
			return true;
		}
		// 拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected Handler mBaseHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_DISMISS_PROGRESS_DIALOG:
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				break;
			case MSG_SHOW_TOAST:
				Utils.showMessage(BaseFragmentActivity.this, (String) msg.obj);
				break;
			case MSG_SHOW_PROGRESS_DIALOG:
				String title = (String) msg.obj;
				if (mProgressDialog == null) {
					mProgressDialog = new MProgressDialog(mContext);
				}
				mProgressDialog.showProgress(title, true);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void sendMessage(int what) {
		mUiMessageHandler.sendEmptyMessage(what);
	}

	public void sendMessage(Message msg) {
		mUiMessageHandler.sendMessage(msg);
	}

	public void sendMessageDelay(int what, Object obj, long delayMillis) {
		mUiMessageHandler.sendMessageDelayed(
				mUiMessageHandler.obtainMessage(what, obj), delayMillis);
	}

	public void post(Runnable r) {
		mUiMessageHandler.post(r);
	}

	public void postDelay(Runnable r, long delayMillis) {
		mUiMessageHandler.postDelayed(r, delayMillis);
	}

	public void removeCallbacks(Runnable r) {
		mUiMessageHandler.removeCallbacks(r);
	}

	public boolean handleMessage(Message arg0) {
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
