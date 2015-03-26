package com.prevention.disease.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.adapter.AdapterAttachExpandable;
import com.prevention.disease.bean.Attach;
import com.prevention.disease.bean.Column;
import com.prevention.disease.bean.Item;
import com.prevention.disease.orm.DAOManager;
import com.prevention.disease.orm.DB;
import com.prevention.disease.tool.Utils;

public class PDFragment extends Fragment {
	Context mContext;
	ListView mListView;
	AdapterAttachExpandable mAdapter;
	Column mColumn;
	ProgressBar detail_loading;
	ScrollView item_scroll;
	final static int SET_PD = 0;
	LinearLayout  item_ll;
	private ArrayList<Item> PDItemList;
	private ArrayList<Attach> PDAttachList;
	private int itemHeight = 40;
	private int  itemIndex;
	/** 屏幕宽度 */
	private int mScreenWidthHeight[];
	private DAOManager mDAOManager;
	DB mDB;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.mContext = activity;
		super.onAttach(activity);
	}
	
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
	
	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			//fragment可见时加载数据
			if(PDAttachList !=null && PDAttachList.size() !=0){
				mHandler.obtainMessage(SET_PD).sendToTarget();
			}else{
				mHandler.obtainMessage(SET_PD).sendToTarget();
			}
		}else{
			//fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
		mListView = (ListView) view.findViewById(R.id.mListView);
		TextView item_textview = (TextView)view.findViewById(R.id.item_textview);
		detail_loading = (ProgressBar)view.findViewById(R.id.detail_loading);
		item_scroll=(ScrollView)view.findViewById(R.id.item_scroll);
		item_ll = (LinearLayout)view. findViewById(R.id.item_ll);
		initItemView();
		return view;
	}

	private void initItemView() {
		mScreenWidthHeight = Utils.getScreenWidthHeight(mContext);
		PDItemList = mDB.getItemDataByColumn(mColumn);
		itemIndex = 0;
		item_ll.removeAllViews();
		if(null!=PDItemList){
			for (int t = 0; t < PDItemList.size(); t++) {
				Item itme = PDItemList.get(t);
				addItemView(itme, t);
			}
		}
	}
	
	private void addItemView(Item item, int index) {
		LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, 100), Utils.dip2px(mContext, itemHeight));
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(tvLayout);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(15);
		textView.setText(item.getName());
		textView.setTag(index);

		LinearLayout.LayoutParams viewLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, 5), Utils.dip2px(mContext, 35));
		View view = new View(mContext);
		view.setLayoutParams(viewLayout);
		view.setBackgroundColor(getResources().getColor(R.color.green));

		LinearLayout.LayoutParams llLayout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout ll = new LinearLayout(mContext);
		ll.setLayoutParams(llLayout);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				LinearLayout ll = (LinearLayout) item_ll.getChildAt(itemIndex);
				String title;
				for (int i = 0; i < item_ll.getChildCount(); i++) {
					View checkView = item_ll.getChildAt(itemIndex);
					int k = checkView.getMeasuredHeight();
					int l = checkView.getTop();
					int i2 = l + k / 2 - mScreenWidthHeight[1] / 2;
					// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
					item_scroll.smoothScrollTo(0, i2);
					// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
					// mItemWidth , 0);
				}
				if (Integer.parseInt(view.getTag().toString()) == itemIndex) {  
					// 刷新数据
					PDAttachList=mDB.getAttachData(PDItemList.get(itemIndex));
					mHandler.obtainMessage(SET_PD).sendToTarget();	
				} else {
					itemIndex = Integer.parseInt(view.getTag().toString());
					ll.removeViewAt(0);
					LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
							Utils.dip2px(mContext, 100), Utils.dip2px(mContext,itemHeight));
					ll.setLayoutParams(tvLayout);
					ll.setBackgroundResource(R.drawable.button_no_press);
					LinearLayout linearlayout = (LinearLayout) item_ll.getChildAt(itemIndex);
					TextView textView = (TextView) linearlayout.getChildAt(0);
					title = textView.getText().toString();
					setItemView(linearlayout, title, itemIndex);
					PDAttachList=mDB.getAttachData(PDItemList.get(itemIndex));
					mHandler.obtainMessage(SET_PD).sendToTarget();	
				}
			}
		});
		if (itemIndex == index) {
			LinearLayout.LayoutParams tvLayoutAgain = new LinearLayout.LayoutParams(
					Utils.dip2px(mContext, 95), Utils.dip2px(mContext, 35));
			textView.setLayoutParams(tvLayoutAgain);
			ll.addView(view);
			ll.addView(textView);
			PDAttachList =mDB.getAttachData(item);
			ll.setBackgroundResource(R.drawable.button_press_item);
		} else {
			ll.setGravity(Gravity.CENTER);
			ll.addView(textView);
			ll.setBackgroundResource(R.drawable.button_no_press);
		}
		ll.setTag(index);
		item_ll.addView(ll);
	}

	private void setItemView(LinearLayout linearLayout, String title, int index) {
		linearLayout.removeAllViews();
		LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, 95), Utils.dip2px(mContext, itemHeight));
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(tvLayout);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(15);
		textView.setText(title);
		textView.setTag(index);

		LinearLayout.LayoutParams viewLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, 5), Utils.dip2px(mContext, itemHeight));
		View view = new View(mContext);
		view.setLayoutParams(viewLayout);
		view.setBackgroundColor(getResources().getColor(R.color.green));

		linearLayout.addView(view);
		linearLayout.addView(textView);
		linearLayout.setBackgroundResource(R.drawable.button_press_item);
		linearLayout.setTag(index);
		
	}
	
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SET_PD:
				detail_loading.setVisibility(View.GONE);
				item_scroll.setVisibility(View.VISIBLE);
				if(null==PDAttachList){
					Toast.makeText(mContext,"没有数据！", Toast.LENGTH_SHORT).show();
				}
				mAdapter = new AdapterAttachExpandable(mContext, PDAttachList);
//				mListView.setAdapter(mAdapter);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}
