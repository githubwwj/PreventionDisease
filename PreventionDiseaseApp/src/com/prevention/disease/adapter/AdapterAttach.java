package com.prevention.disease.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prevention.disease.R;
import com.prevention.disease.bean.Attach;

public class AdapterAttach extends BaseAdapter {
	private LayoutInflater mInflater;
	private  ViewList viewList = null;
	private ArrayList<Attach> mAttachList;
	
	public AdapterAttach(Context context,ArrayList<Attach> attach) {
		mInflater = LayoutInflater.from(context);
		this.mAttachList=attach;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(null==mAttachList||mAttachList.size()==0){
			return 0;
		}
		return mAttachList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAttachList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(  int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 
		Attach attach =mAttachList.get(position);
		if (convertView == null) {
					viewList = new ViewList();
					convertView = mInflater.inflate(R.layout.attach_lv_item, null);
					viewList.attach_content = (TextView) convertView.findViewById(R.id.attach_content);
					viewList.attach_title = (TextView) convertView.findViewById(R.id.attach_title);
					viewList.viewGreeen = (View) convertView.findViewById(R.id.viewGreeen);
					convertView.setTag(viewList);
		} else {
			viewList = (ViewList) convertView.getTag();
		}
		
		if(attach.getContent()==null){
			viewList.attach_content.setVisibility(View.GONE);
			viewList.attach_content.setText("");
		}else{
			viewList.attach_content.setVisibility(View.VISIBLE);
			viewList.attach_content.setText(attach.getContent());
		}
		if(attach.getTitle()==null){
			viewList.attach_title.setVisibility(View.GONE);
			viewList.viewGreeen.setVisibility(View.VISIBLE);
			viewList.attach_title.setText("");
		}else{
			viewList.attach_title.setVisibility(View.VISIBLE);
			viewList.viewGreeen.setVisibility(View.GONE);
			viewList.attach_title.setText(attach.getTitle());
		}
		return convertView;
	}
	
	public    class ViewList {
		public View viewGreeen;
		public TextView attach_title;
		private  TextView attach_content;		//ÄÚÈÝ
		
	}
	
}
