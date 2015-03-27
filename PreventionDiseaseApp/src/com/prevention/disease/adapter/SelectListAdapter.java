package com.prevention.disease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SelectListAdapter<T> extends BaseAdapter{

	private LayoutInflater mInflater;
	private T[] dataList;
	private ListViewListerner<T> listerner;
	private int listLayoutId;
	private ItemClickListener selectListener;
	
	public SelectListAdapter(Context context,T[] list,int listItemLayoutId,ListViewListerner<T> listerner,ItemClickListener selectListener) {
		mInflater = LayoutInflater.from(context);
		this.dataList=list;
		this.listerner=listerner;
		this.listLayoutId=listItemLayoutId;
		this.selectListener=selectListener;
	}

	
	public T[] getDataList() {
		return dataList;
	}


	public void setDataList(T[] dataList) {
		this.dataList = dataList;
	}


	@Override
	public int getCount() {
		if(this.dataList != null){
			return this.dataList.length;
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		
		return this.dataList[arg0];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(listLayoutId, null);
		}
        convertView=listerner.getListView(convertView,dataList[position]);  
        convertView.setTag(position);
        
        convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				selectListener.onClick(position, arg0);
			}
        });
        return convertView;
	}
	
	public interface ListViewListerner<T> {
		public View getListView(View convertView, T data);
	}

	public interface ItemClickListener {
		public void onClick(int index, View view);
	}
}