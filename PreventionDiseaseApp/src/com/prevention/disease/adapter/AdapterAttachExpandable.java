package com.prevention.disease.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.prevention.disease.R;
import com.prevention.disease.bean.Attach;
import com.prevention.disease.bean.AttachOverride;

public class AdapterAttachExpandable extends BaseExpandableListAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Attach> mAttachList;
	
	public AdapterAttachExpandable(Context context,ArrayList<Attach> attach) {
		mInflater = LayoutInflater.from(context);
		this.mAttachList=attach;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mAttachList != null ? mAttachList.size() : 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (mAttachList != null) {
			if (mAttachList.get(groupPosition).getAttachOverrides() != null) {
				return mAttachList.get(groupPosition).getAttachOverrides().size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if ((mAttachList != null && mAttachList.size() > 0)
				&& (groupPosition > 0 && groupPosition < mAttachList
						.size())) {
			return mAttachList.get(groupPosition);
		} else {
			return null;
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if ((mAttachList != null && mAttachList.size() > 0)
				&& (groupPosition > 0 && groupPosition < mAttachList
						.size())) {
			if (mAttachList.get(groupPosition).getAttachOverrides() != null
					&& mAttachList.get(groupPosition).getAttachOverrides().size() > 0
					&& (childPosition > 0 && childPosition < mAttachList.get(groupPosition).getAttachOverrides().size())) {
				return mAttachList.get(groupPosition).getAttachOverrides().get(childPosition);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.attach_group,null);
			holder = new GroupHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.update(mAttachList.get(groupPosition));
		return convertView;
	}

	class GroupHolder {
		private TextView attachTV;

		public GroupHolder(View v) {
			this.attachTV = (TextView) v.findViewById(R.id.attach_text);
		}

		public void update(Attach attach) {
			if(null!=attach){
				String title= attach.getTitle();
				if(null!=title)
					attachTV.setText(title);
				else
					attachTV.setText("");
			}
		}
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.attach_override_item,null);
			holder = new ChildHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		holder.update(mAttachList.get(groupPosition).getAttachOverrides().get(childPosition));
		return convertView;
	}
	
	class ChildHolder {
		private TextView childTV;

		public ChildHolder(View v) {
			this.childTV = (TextView) v.findViewById(R.id.attach_override_text);
		}

		public void update(AttachOverride attachOverride) {
			if(attachOverride!=null){
				String content= attachOverride.getContent();
				if(null!=content)
					childTV.setText(content);
				else
					childTV.setText("");
			}
		}
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
