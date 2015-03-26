package com.prevention.disease.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prevention.disease.R;

public class MProgressDialog {
	
	
	private LayoutInflater inflater = null;
	private Dialog loadingDialog;
	private TextView tipTextView;
	private Context mContext;
	
	public MProgressDialog(Context context){
		mContext=context;
	}
	
	/**
	 * 
	 * @param text   设置话框的文本内容
	 * @param isCloseSelf  点击取消是否关闭对话框   
	 * @return
	 */
	public Dialog showProgress(String text, boolean isCloseSelf) {

		if (inflater == null)
			inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view  
		v.setFocusableInTouchMode(true);
		tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
		if (text != null && !text.equals("")) {
			tipTextView.setText(text);// 设置加载文字  
		}
		if (loadingDialog == null) {
			loadingDialog = new Dialog(mContext, R.style.loading_dialog);// 创建自定义样式dialog  
			loadingDialog.setCanceledOnTouchOutside(false);//点击对话框外边界不消失
			if (!isCloseSelf) {
				loadingDialog.setCancelable(false);// 不可以用“返回键”取消  
			} else {
				loadingDialog.setCancelable(true);// 可以用“返回键”取消  
			}
			loadingDialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));// 设置布局  
			loadingDialog.show();
		} else {
			loadingDialog.show();
			v.setOnKeyListener(new View.OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					dismiss();
					return false;
				}
			});
		}
		return loadingDialog;
	}


	public void dismiss(){
		if (loadingDialog != null && loadingDialog.isShowing())
			loadingDialog.dismiss();
	}
	
}
