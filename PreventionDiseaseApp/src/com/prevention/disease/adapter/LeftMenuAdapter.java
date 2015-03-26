package com.prevention.disease.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prevention.disease.R;

public class LeftMenuAdapter extends SyncAdapter {

	private List<Bitmap> bitmapList;
	LayoutInflater mLayoutInflater;

	public LeftMenuAdapter(Context context, List<String> mLables,
			List<Bitmap> list) {
		super(context, mLables);
		this.bitmapList = list;
		mLayoutInflater=LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.leftmenuitem, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.update(getDataList().get(position).toString(),
				bitmapList.get(position));

		return convertView;
	}

	private class ViewHolder {
		public ImageView imgView;
		public TextView textView;

		public ViewHolder(View v) {
			this.imgView = (ImageView) v.findViewById(R.id.left_menu_image);
			this.textView = (TextView) v.findViewById(R.id.left_menu_text);
		}

		public void update(String title, Bitmap bm) {
			if (!"".equals(title) && title.length() > 0)
				textView.setText(title);
			if (bm != null)
				imgView.setImageBitmap(bm);
		}

	}

}
