package com.prevention.disease.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.base.BaseActivity;
import com.prevention.disease.tool.CommonUtil;

public class InfobackActivity extends BaseActivity implements OnClickListener {

	private Button sendBtn;
	private EditText mSendContent;
	private ImageView mCancleBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_feedbacka);
		findById();
	}
	
	/**
	 * 获取传递的参数
	 */
	protected void analyseIntent() {
		// TODO Auto-generated method stub
		
	}

	private void findById() {
		mCancleBtn = (ImageView) findViewById(R.id.b_back);
		sendBtn= (Button) findViewById(R.id.sendBtn);
		mSendContent=(EditText) findViewById(R.id.sendContent);
		mCancleBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_back:
			finish();
			break;
		case R.id.sendBtn:
			mSendContent.getText().toString();
			break;
		default:
			break;
		}
	}


	public void queryItem() {
		if (!CommonUtil.checkNetState(this)) {
			Toast.makeText(this,
					getResources().getString(R.string.open_network),
					Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// ItemUrl = DiibearApplication.HOST + "/api/centers/";
				// try {
				// Message msg=new Message();
				// String result =
				// HttpClientImp.getInstance().getForString(ItemUrl, null);
				// if(null!=result){
				// mListItem=JsonParser.parserItemJson(result);
				// msg.what=DEFAULT_LOAD_OK;
				// }else{
				// msg.what=NO_DATA;
				// }
				// mHandler.sendMessage(msg);
				//
				// } catch (Exception e) {
				// e.printStackTrace();
				// mHandler.sendEmptyMessage(LOAD_ERROR);
				// }
			}
		}).start();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		};
	};


}