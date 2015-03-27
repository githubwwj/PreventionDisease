package com.prevention.disease.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.adapter.PreventionDiseaseFragmentPagerAdapter;
import com.prevention.disease.base.BaseFragmentActivity;
import com.prevention.disease.bean.Column;
import com.prevention.disease.bean.Item;
import com.prevention.disease.fragment.PDItemFragment;
import com.prevention.disease.orm.DB;
import com.prevention.disease.tool.Utils;

/**
 * 预防常见病体系 author:WWJ
 */
public class PreventionActivity extends BaseFragmentActivity {
	PreventionDiseaseFragmentPagerAdapter mAdapetr;
	private ViewPager mViewPager;
	private ArrayList<Column> PDColumnList;
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 左阴影部分 */
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidthHeight[];
	ArrayList<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();

	private	DB mDB;
	private int mColumnHeight = 27;
	private int mColumnMinWidth = 67;
	private int mLineHeight =3;
	private int mColumnTextSize=15;
	private LinearLayout mColumnLL;
	private HorizontalScrollView mHorizontalScrollView;
	private int mInitData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.prevention_main);
		super.onCreate(savedInstanceState);
		mScreenWidthHeight = Utils.getScreenWidthHeight(this);
	}
	
	/**
	 * 初始化组件
	 */
	@Override
	protected void initWidgetProperty() {
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.item_scroll);
		mColumnLL = (LinearLayout) findViewById(R.id.item_ll);
		initData(mInitData);
		super.initWidgetProperty();
	}
	
	/**
	 * 获取传递的参数
	 */
	protected void analyseIntent() {
		// TODO Auto-generated method stub
		mDB = DB.getInstance(this);
		mInitData = getIntent().getIntExtra("initData", 1);
		
	}

	/**
	 * 初始化组件的事件
	 */
	protected void initWidgetEvent() {
		super.initWidgetEvent();
	}


	/**
	 * 初始化Column栏目项
	 * */
	private void initTabColumn() {
		columnSelectIndex = 0;
		if (PDColumnList == null) {
			Toast.makeText(this, "没有数据！", Toast.LENGTH_SHORT).show();
			return;
		}
		mScreenWidthHeight = Utils.getScreenWidthHeight(mContext);
		mColumnLL.removeAllViews();
		if (null != PDColumnList) {
			for (int t = 0; t < PDColumnList.size(); t++) {
				Column column = PDColumnList.get(t);
				addColumnView(column, t);
			}
		}
	}

	private void addColumnView(Column column, int index) {
		LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, mColumnMinWidth), Utils.dip2px(mContext,mColumnHeight));
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(tvLayout);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(mColumnTextSize);
		textView.setText(column.getName());
		textView.setTextColor(getResources().getColor(R.color.dark_green));
		textView.setTag(index);

		LinearLayout.LayoutParams viewLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, mColumnMinWidth), Utils.dip2px(mContext, mLineHeight));
		View view = new View(mContext);
		view.setLayoutParams(viewLayout);
		view.setBackgroundColor(getResources().getColor(R.color.dark_green));

		LinearLayout.LayoutParams llLayout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout ll = new LinearLayout(mContext);
		ll.setLayoutParams(llLayout);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				LinearLayout ll = (LinearLayout) mColumnLL.getChildAt(columnSelectIndex);
				for (int i = 0; i < mColumnLL.getChildCount(); i++) {
					View checkView = mColumnLL.getChildAt(columnSelectIndex);
					int k = checkView.getMeasuredWidth();
					int l = checkView.getLeft();
					int i2 = l + k / 2 - mScreenWidthHeight[0] / 2;
					mHorizontalScrollView.smoothScrollTo(i2, 0);
				}
				if (Integer.parseInt(view.getTag().toString()) == columnSelectIndex) {
					mViewPager.setCurrentItem(columnSelectIndex);
				} else {
					columnSelectIndex = Integer.parseInt(view.getTag().toString());
					ll.removeViewAt(1);
					TextView textView = (TextView) ll.getChildAt(0);
					textView.setTextColor(getResources().getColor(R.color.black));
					LinearLayout linearlayout = (LinearLayout) mColumnLL.getChildAt(columnSelectIndex);
					setItemView(linearlayout, columnSelectIndex);
					mViewPager.setCurrentItem(columnSelectIndex);
				}
			}
		});
		if (columnSelectIndex == index) {
			textView.setLayoutParams(tvLayout);
			textView.setTextColor(getResources().getColor(R.color.dark_green));
			ll.addView(textView);
			ll.addView(view);
		} else {
			textView.setTextColor(getResources().getColor(R.color.black));
			ll.setGravity(Gravity.CENTER);
			ll.addView(textView);
		}
		ll.setTag(index);
		mColumnLL.addView(ll);
	}

	private void setItemView(LinearLayout linearLayout, int index) {
		String title = ((TextView) linearLayout.getChildAt(0)).getText().toString();
		linearLayout.removeAllViews();
		LinearLayout.LayoutParams tvLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, mColumnMinWidth), Utils.dip2px(mContext,mColumnHeight));
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(tvLayout);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(mColumnTextSize);
		textView.setText(title);
		textView.setTag(index);
		textView.setTextColor(getResources().getColor(R.color.dark_green));

		LinearLayout.LayoutParams viewLayout = new LinearLayout.LayoutParams(
				Utils.dip2px(mContext, mColumnMinWidth), Utils.dip2px(mContext, mLineHeight));
		View view = new View(mContext);
		view.setLayoutParams(viewLayout);
		view.setBackgroundColor(getResources().getColor(R.color.dark_green));

		linearLayout.addView(textView);
		linearLayout.addView(view);
		linearLayout.setTag(index);

	}

	/**
	 * 初始化Fragment
	 * */
	private void initFragment() {
		for (int k = 0; k < PDColumnList.size(); k++) {
			PDItemFragment pdFragment = new PDItemFragment();
			Bundle args = new Bundle();
			args.putSerializable("column", PDColumnList.get(k));
			pdFragment.setArguments(args);
			fragments.add(pdFragment);
		}
		mAdapetr = new PreventionDiseaseFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			selectTab(position);
		}
	};

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		LinearLayout ll = (LinearLayout) mColumnLL.getChildAt(columnSelectIndex);
		for (int i = 0; i < mColumnLL.getChildCount(); i++) {
			View checkView = mColumnLL.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidthHeight[0] / 2;
			mHorizontalScrollView.smoothScrollTo(i2, 0);
		}
		if (tab_postion == columnSelectIndex) {
			mViewPager.setCurrentItem(columnSelectIndex);
		} else {
			columnSelectIndex = tab_postion;
			ll.removeViewAt(1);
			TextView textView = (TextView) ll.getChildAt(0);
			textView.setTextColor(getResources().getColor(R.color.black));
			LinearLayout linearlayout = (LinearLayout) mColumnLL.getChildAt(columnSelectIndex);
			setItemView(linearlayout, columnSelectIndex);
		}
	}


	private void initData(int initIndex) {
		int wh[] = Utils.getScreenWidthHeight(this);
		if (initIndex == 1) {
			PDColumnList = mDB.getColumnData(initIndex);
			initTabColumn();
			initFragment();
		} else if (initIndex == 2) {
			mColumnMinWidth = Utils.px2dip(this, wh[0] / 3);
			PDColumnList = mDB.getColumnData(initIndex);
			initTabColumn();
			initFragment();
		} else if (initIndex == 3) {
			if (5 * Utils.dip2px(this, 105) < mColumnMinWidth) {
				mColumnMinWidth = Utils.px2dip(this, wh[0] / 5);
			}
			PDColumnList = mDB.getColumnData(initIndex);
			initTabColumn();
			initFragment();
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
    		}
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
	// @Override
	// public void onClick(View view) {
	// // TODO Auto-generated method stub
	// if (view.getId() == R.id.firstButton) {
	// mAdapetr.setFragments(fragments);
	// initData(1);
	// } else if (view.getId() == R.id.secondButton) {
	// mAdapetr.setFragments(fragments);
	// initData(2);
	// } else if (view.getId() == R.id.thirdButton) {
	// mAdapetr.setFragments(fragments);
	// initData(3);
	// } else if (view.getId() == R.id.search) {
	// mContext.startActivity(new Intent(mContext,ChoosePDActivity.class));
	// mContext.overridePendingTransition(R.anim.slide_in_right,
	// R.anim.slide_out_left);
	// }
	// }
}
