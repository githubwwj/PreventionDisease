package com.prevention.disease.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.activity.PDAttachActivity;
import com.prevention.disease.adapter.AdapterItem;
import com.prevention.disease.base.BaseFragment;
import com.prevention.disease.bean.Column;
import com.prevention.disease.bean.Item;
import com.prevention.disease.orm.DAOManager;
import com.prevention.disease.orm.DB;
import com.prevention.disease.tool.LogUtil;

/**
 * 
 *预防常见病体系 author:WWJ
 *12/13
 */
public class PDItemFragment extends BaseFragment implements OnItemClickListener{
	private ListView mListView;
	private Column mColumn;
	private ProgressBar detail_loading;
	final static int SET_PD = 111;
	private ArrayList<Item> mPDItemList=new ArrayList<Item>();
	private DAOManager mDAOManager;
	private DB mDB;
	private AdapterItem mAdapterItem;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		mDAOManager=DAOManager.getInstance(mContext);
		if(null!=args.getSerializable("column")){
			mColumn =(Column) args.getSerializable("column");
		}
		mDB=DB.getInstance(mContext);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.item_fragment);
		LogUtil.i("info","====================onCreateView==============");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	protected void initWidgetProperty() {
		mListView = (ListView) mView.findViewById(R.id.mListView);
		detail_loading = (ProgressBar)mView.findViewById(R.id.detail_loading);
		mAdapterItem=new AdapterItem(mContext, mPDItemList);
		mListView.setAdapter(mAdapterItem);
		super.initWidgetProperty();
	}
	
	protected void initWidgetEvent() {
		mListView.setOnItemClickListener(this);
	};
	
	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		mPDItemList.clear();
		if(null!= mDB.getItemDataByColumn(mColumn)){
			for(int d=0;d<mDB.getItemDataByColumn(mColumn).size();d++){
				mPDItemList.add(mDB.getItemDataByColumn(mColumn).get(d));
			}
		}
		sendMessage(SET_PD);
	}
	
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case SET_PD:
			detail_loading.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			if(null==mPDItemList){
				Toast.makeText(mContext,"没有数据！", Toast.LENGTH_SHORT).show();
			}
			mAdapterItem.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return super.handleMessage(msg);
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Item item=(Item)mListView.getItemAtPosition(position);
		Intent intent=new Intent(mContext,PDAttachActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("item", item);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
