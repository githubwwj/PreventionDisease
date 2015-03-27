package com.prevention.disease.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class SyncAdapter extends BaseAdapter {

	private Context mContext;
	private List<? extends Object> mDataList;
	private LayoutInflater mInflater;

	public SyncAdapter(Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public SyncAdapter(Context context, List<? extends Object> dataList) {
		this.mContext = context;
		this.mDataList = dataList;
		this.mInflater = LayoutInflater.from(context);	
	}

	@Override
	public int getCount() {
		return mDataList != null ? mDataList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		if ((mDataList != null && mDataList.size() > 0)
				&& (position > 0 && position < mDataList.size()))
			return mDataList.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public Context getContext() {
		return mContext;
	}

	public LayoutInflater getInflater() {
		return mInflater;
	}

	public List<? extends Object> getDataList() {
		return mDataList;
	}

	public void setDataList(List<? extends Object> mDataList) {
		this.mDataList = mDataList;
	}

}
