package com.prevention.disease.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prevention.disease.R;
import com.prevention.disease.bean.Item;

public class AdapterItem extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Item> mItemList;

	public AdapterItem(Context context, ArrayList<Item> items) {
		mInflater = LayoutInflater.from(context);
		this.mItemList = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null == mItemList) {
			return 0;
		}
		return mItemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewList viewList = null;
		Item attach = mItemList.get(position);
		if (convertView == null) {
			viewList = new ViewList();
			convertView = mInflater.inflate(R.layout.item_item, null);
			viewList.attach_text = (TextView) convertView.findViewById(R.id.attach_text);
			viewList.attach_index = (TextView) convertView.findViewById(R.id.attach_index);
			convertView.setTag(viewList);
		} else {
			viewList = (ViewList) convertView.getTag();
		}

		if (attach.getName() == null) {
			viewList.attach_text.setText("");
		} else {
			viewList.attach_text.setText(attach.getName());
		}
		viewList.attach_index.setText(attach.getItemId()+"");
		return convertView;
	}

	public class ViewList {
		public TextView attach_index;
		private TextView attach_text; // ÄÚÈÝ

	}

}
