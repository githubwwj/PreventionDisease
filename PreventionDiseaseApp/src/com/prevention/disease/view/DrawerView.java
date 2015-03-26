package com.prevention.disease.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.prevention.disease.R;
import com.prevention.disease.activity.AboutUSActivity;
import com.prevention.disease.activity.PreventionActivity;
import com.prevention.disease.adapter.LeftMenuAdapter;
/** 
 * 自定义SlidingMenu 测拉菜单类
 * */
public class DrawerView implements OnClickListener,OnItemClickListener{
	private final Activity activity;
	SlidingMenu localSlidingMenu;
	private SwitchButton night_mode_btn;
	private TextView night_mode_text;
	private RelativeLayout feedback_btn,rhintis_prevention_desease,about_btn;
	private ListView mListView;
	private LeftMenuAdapter menuAdapter;
	private List<Bitmap> iconList;
	private List<String> labels = new ArrayList<String>();
	private Bitmap moments;
	private Bitmap attendance;
	private Bitmap portfolio;
	private Bitmap announcement;
	private Bitmap newsletters;
	private Bitmap signout;
	private Bitmap about;
	
	public DrawerView(Activity activity) {
		this.activity = activity;
	}

	public SlidingMenu initSlidingMenu() {
		localSlidingMenu = new SlidingMenu(activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT);//设置左右滑菜单
		localSlidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);//设置要使菜单滑动，触碰屏幕的范围
		localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影图片的宽度
		localSlidingMenu.setShadowDrawable(R.drawable.shadow);//设置阴影图片
		localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu划出时主页面显示的剩余宽度
		localSlidingMenu.setFadeDegree(0.35F);//SlidingMenu滑动时的渐变程度
		localSlidingMenu.attachToActivity(activity, SlidingMenu.RIGHT);//使SlidingMenu附加在Activity右边
		localSlidingMenu.setMenu(R.layout.left_drawer_fragment);//设置menu的布局文件
//		localSlidingMenu.toggle();//动态判断自动关闭或开启SlidingMenu
//		localSlidingMenu.setSecondaryMenu(R.layout.profile_drawer_right);
//		localSlidingMenu.setSlidingEnabled(false);
//		localSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
//		localSlidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
//					public void onOpened() {
//						
//					}
//				});
		
		initView();
		return localSlidingMenu;
	}

	private void initView() {
//		night_mode_btn = (SwitchButton)localSlidingMenu.findViewById(R.id.night_mode_btn);
//		night_mode_text = (TextView)localSlidingMenu.findViewById(R.id.night_mode_text);
//		night_mode_btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(isChecked){
//					night_mode_text.setText(activity.getResources().getString(R.string.action_night_mode));
//				}else{
//					night_mode_text.setText(activity.getResources().getString(R.string.action_day_mode));
//				}
//			}
//		});
//		night_mode_btn.setChecked(false);
//		if(night_mode_btn.isChecked()){
//			night_mode_text.setText(activity.getResources().getString(R.string.action_night_mode));
//		}else{
//			night_mode_text.setText(activity.getResources().getString(R.string.action_day_mode));
//		}
//		mListView = (ListView) localSlidingMenu.findViewById(R.id.menulist);
		feedback_btn =(RelativeLayout)localSlidingMenu.findViewById(R.id.feedback_btn);
		rhintis_prevention_desease =(RelativeLayout)localSlidingMenu.findViewById(R.id.rhintis_prevention_desease);
		about_btn =(RelativeLayout)localSlidingMenu.findViewById(R.id.about_btn);
		rhintis_prevention_desease.setOnClickListener(this);
		about_btn.setOnClickListener(this);
		feedback_btn.setOnClickListener(this);
	}
	
	
	private void setAdapter(){
		iconList = decodeBitmap();
		String[] labels =activity.getResources().getStringArray(R.array.labels);
		for (int i = 0; i < labels.length; i++) {
			this.labels.add(labels[i]);
		}
		menuAdapter = new LeftMenuAdapter(activity, this.labels, iconList);
		mListView.setAdapter(menuAdapter);
		mListView.setOnItemClickListener(this);
	}
	
	private List<Bitmap> decodeBitmap() {
		List<Bitmap> iconList = new ArrayList<Bitmap>();

		moments = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.moments_icon);
		announcement = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.announcement_icon);

		newsletters = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.newsletters_icon);
		
		about = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.about_icon);

		signout = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.log_out_icon);

		iconList.add(moments);
		iconList.add(attendance);
		iconList.add(portfolio);
		iconList.add(announcement);
		iconList.add(newsletters);
		iconList.add(about);
		iconList.add(signout);

		return iconList;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.feedback_btn:
				//系统邮件系统的动作为android.content.Intent.ACTION_SEND
				Intent email=new Intent(Intent.ACTION_SEND);
				email.setType("plain/text");
				email.putExtra(android.content.Intent.EXTRA_EMAIL, "wwj_computeryes@163.com");
				email.putExtra(Intent.EXTRA_SUBJECT, "反馈"); 
				activity.startActivity(email); 
				break;
			case R.id.rhintis_prevention_desease:
				activity.startActivity(new Intent(activity,PreventionActivity.class));
				activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				activity.finish();
				break;
			case R.id.about_btn:
				activity.startActivity(new Intent(activity,AboutUSActivity.class));
				break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		activity.startActivity(new Intent(activity,PreventionActivity.class));
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
}
