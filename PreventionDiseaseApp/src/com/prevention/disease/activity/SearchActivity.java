package com.prevention.disease.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.adapter.SelectListAdapter;
import com.prevention.disease.adapter.SelectListAdapter.ItemClickListener;
import com.prevention.disease.adapter.SelectListAdapter.ListViewListerner;
import com.prevention.disease.base.BaseActivity;
import com.prevention.disease.bean.Item;
import com.prevention.disease.orm.DB;
import com.prevention.disease.tool.ConstantCode;
import com.prevention.disease.tool.StringUtil;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private EditText mSearchEdit;
	private Button mCancleBtn;
	private ListView listview_prompt;
	private String ItemUrl;
	private final int DEFAULT_LOAD_OK = 1;
	private final int NET_ERROR = 3;
	private final int LOAD_ERROR = 4;
	private final int NO_DATA = 5;
	private SelectListAdapter<Item> adapter;
	private Item[] baseArr;
	private List<Item> disheList;
	private Item mItem;
	private ArrayList<Item> mListItem = new ArrayList<Item>();
	private InputMethodManager imm;
	private DB mDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searcha);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		findById();
	}
	
	/**
	 * 获取传递的参数
	 */
	protected void analyseIntent() {
		// TODO Auto-generated method stub
		mDB = DB.getInstance(this);
		
	}

	private void findById() {
		mSearchEdit = (EditText) findViewById(R.id.e_select_school);
		mCancleBtn = (Button) findViewById(R.id.b_cancle);
		listview_prompt = (ListView) findViewById(R.id.lv_prompt);

		mCancleBtn.setOnClickListener(this);
		mSearchEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (mListItem != null && mListItem.size() > 0) {
					if ("".equals(s.toString())) {
						baseArr = null;
					} else {
						disheList = new ArrayList<Item>();
						int i = 0;

						for (Item dishe : mListItem) {

							if (StringUtil.notNull(dishe.getName())
									.toUpperCase()
									.indexOf(s.toString().toUpperCase()) != -1) {
								i++;
								disheList.add(dishe);
								if (i == 20)
									break; // 取20条
							}
						}
						baseArr = new Item[disheList.size()];
						baseArr = disheList.toArray(baseArr);
					}
					if (null == adapter) {
						adapter = new SelectListAdapter<Item>(
								SearchActivity.this, baseArr,
								R.layout.select_item_layout,
								new BaseViewListener(),
								new BaseItemClickListener());
					}
					adapter.setDataList(baseArr);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(SearchActivity.this,
							getResources().getString(R.string.no_data),
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_cancle:
			imm.hideSoftInputFromWindow(mCancleBtn.getWindowToken(), 0);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mListItem == null || mListItem.size() == 0) {
			queryItem();
		}
	}

	public void queryItem() {
//		if (!CommonUtil.checkNetState(this)) {
//			Toast.makeText(this,
//					getResources().getString(R.string.open_network),
//					Toast.LENGTH_SHORT).show();
//			return;
//		}
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
				if (null != mDB.getColumnData(1)) {
					for (int pd = 0; pd < mDB.getColumnData(1).size(); pd++) {
						if (mDB.getColumnData(pd) != null) {
							for (int i = 0; i < mDB.getColumnData(pd).size(); i++) {
								ArrayList<Item> items = mDB
										.getItemDataByColumn(mDB.getColumnData(
												pd).get(i));
								if (items != null) {
									for (int j = 0; j < items.size(); j++) {
										mListItem.add(items.get(j));
									}
								}
							}
						}
					}
				}
				disheList = new ArrayList<Item>();
				int i = 0;

				for (Item dishe : mListItem) {
						i++;
						disheList.add(dishe);
						if (i == 20)
							break; // 取20条
				}
				baseArr = new Item[disheList.size()];
				baseArr = disheList.toArray(baseArr);
				mHandler.sendEmptyMessage(DEFAULT_LOAD_OK);
			}
		}).start();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NET_ERROR:
				Toast.makeText(SearchActivity.this,
						getResources().getString(R.string.network_error),
						Toast.LENGTH_SHORT).show();
				break;
			case DEFAULT_LOAD_OK:
				try {
					adapter = new SelectListAdapter<Item>(
							SearchActivity.this, baseArr,
							R.layout.select_item_layout,
							new BaseViewListener(), new BaseItemClickListener());
					listview_prompt.setAdapter(adapter);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case NO_DATA:
				Toast.makeText(SearchActivity.this,
						getResources().getString(R.string.no_data),
						Toast.LENGTH_LONG).show();
				break;
			case LOAD_ERROR:
				Toast.makeText(SearchActivity.this,
						getResources().getString(R.string.network_error),
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @author wwj ListView 中的每一条数据
	 */
	public class BaseViewListener implements ListViewListerner<Item> {

		@Override
		public View getListView(View convertView, Item data) {
			TextView name = (TextView) convertView
					.findViewById(R.id.list_item_name);
			name.setText(data.getName());
			return convertView;
		}

	}

	private class BaseItemClickListener implements ItemClickListener {

		@Override
		public void onClick(int index, View view) {
			mItem = baseArr[index];
			Intent data=new Intent();
			Bundle bundle=new Bundle();
			bundle.putSerializable("item", mItem);
			data.putExtras(bundle);
			setResult(20, data);
			finish();
		}
	}
	
	/** 
     * 复写onActivityResult，这个方法 
     * 是要等到SimpleTaskActivity点了提交过后才会执行的 
     */  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
        //可以根据多个请求代码来作相应的操作  
        if(20==resultCode)  
        {  
        	if(null!=data.getExtras().getSerializable("item")){
        		Item	item =(Item) data.getExtras().getSerializable("item");
        		Intent intent=new Intent(mContext,PDAttachActivity.class);
        		Bundle bundle=new Bundle();
        		bundle.putSerializable("item", item);
        		intent.putExtras(bundle);
        		startActivity(intent);
        		finish();
    		}
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  

}