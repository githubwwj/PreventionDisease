package com.prevention.disease.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.activity.SearchActivity;
import com.prevention.disease.constants.ConstantsCode;
import com.prevention.disease.dialog.MProgressDialog;
import com.prevention.disease.tool.Utils;

public class BaseActivity extends Activity implements OnClickListener, Callback {
	protected ImageView mBack;
	protected ImageView mSearch;
	
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

	protected String TAG = "BaseActivity";

	public String getClassName() {
		String clazzName = getClass().getName();
		return clazzName;
	}

	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.b_back:
			if(confirmToFinishSelf()){
				finish();
			}
			break;
		case R.id.search:
			intent=new Intent(mContext,SearchActivity.class);
			startActivityForResult(intent, 100);
			break;
		default:
			break;
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		analyseIntent();
		initWidgetProperty();
		initWidgetEvent();
	}

	protected void analyseIntent() {
	}

	protected boolean checkForms() {
		return true;
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
		addWidgetEventListener(mBack);
		addWidgetEventListener(mSearch);
	}     


	protected void setTitle(String title){
		if(null!=mTitle&&null!=title){
			mTitle.setText(title);
		}
	}
	
	protected void initWidgetProperty() {
		mBack = (ImageView) findViewById(R.id.b_back);
		mSearch = (ImageView) findViewById(R.id.search);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mProgressDialog != null) {
			mBaseHandler.sendEmptyMessage(MSG_DISMISS_PROGRESS_DIALOG);
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
				Utils.showMessage(BaseActivity.this, (String) msg.obj);
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
	
	protected boolean confirmToFinishSelf() {
        return true;
    }
}
