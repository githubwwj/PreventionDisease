package com.prevention.disease.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.prevention.disease.dialog.MProgressDialog;
import com.prevention.disease.tool.Utils;

public class BaseFragment extends Fragment implements OnClickListener, Callback {
	protected Context mContext;
	protected View mView;
	private MProgressDialog mProgressDialog;
	private Handler mUiMessageHandler;
	private int mContentLayout;
	private static final int MSG_DISMISS_PROGRESS_DIALOG = 0;
	private static final int MSG_SHOW_TOAST = 1;
	private static final int MSG_SHOW_PROGRESS_DIALOG = 2;

	@Override
	public void onAttach(Activity activity) {
		this.mContext = activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		analyseIntent();
		super.onCreate(savedInstanceState);
	}

	protected void analyseIntent() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(mContentLayout, null);
		initWidgetProperty();
		initWidgetEvent();
		getData();
		return mView;
	}

	protected void setContentView(int ContentLayout) {
		mContentLayout = ContentLayout;
	}

	protected void initWidgetProperty() {
		mUiMessageHandler = new Handler(this);
	}

	protected void getData() {
	}
	
	protected void initWidgetEvent() {
	}

	protected void addWidgetEventListener(View v) {
		if (v != null)
			v.setOnClickListener(this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void onClick(View v) {

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
		intent.setClass(mContext, activityClass);

		if (bundle != null) {
			intent.putExtras(bundle);
		}

		if (isReturn) {
			startActivityForResult(intent, requestCode);
		} else {
			startActivity(intent);
		}

		if (isFinish) {
			getActivity().finish();
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

	protected Handler mBaseHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_DISMISS_PROGRESS_DIALOG:
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				break;
			case MSG_SHOW_TOAST:
				Utils.showMessage(mContext, (String) msg.obj);
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
}
