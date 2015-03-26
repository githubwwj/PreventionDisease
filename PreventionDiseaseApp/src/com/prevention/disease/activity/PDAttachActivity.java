package com.prevention.disease.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prevention.disease.R;
import com.prevention.disease.adapter.AdapterAttach;
import com.prevention.disease.adapter.AdapterAttachExpandable;
import com.prevention.disease.base.BaseActivity;
import com.prevention.disease.bean.Attach;
import com.prevention.disease.bean.Item;
import com.prevention.disease.orm.DAOManager;
import com.prevention.disease.orm.DB;
import com.prevention.disease.tool.ConstantCode;
import com.prevention.disease.tool.StringUtil;

/**
 * 
 *预防常见病体系 author:WWJ
 *12/13
 */
public class PDAttachActivity extends BaseActivity implements OnClickListener{
	private ExpandableListView mExpandableListView;
	private ListView mListView;
	private Item mItem;
	private ProgressBar detail_loading;
	private final int EXPANDABLEVISITITY=ConstantCode.MSG_PDATTACH;
	private final int LVVISITITY=ConstantCode.MSG_PDATTACH+1;
	public ArrayList<Attach> mPDAttachList=new ArrayList<Attach>();
	private DAOManager mDAOManager;
	private DB mDB;
	private AdapterAttachExpandable mExpandableAdapter;
	private TextView mAttachText;
	private SharedPreferences mSharedPreferences;
	private AdapterAttach mLVAdapter;
	private boolean bLV=false;
	protected ImageView mShare;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.attacha);
		mSharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void initWidgetProperty() {
		super.initWidgetProperty();
		mDAOManager=DAOManager.getInstance(mContext);
		if(null!=getIntent().getExtras().getSerializable("item")){
			mItem =(Item) getIntent().getExtras().getSerializable("item");
		}
		mDB=DB.getInstance(mContext);
		mExpandableListView = (ExpandableListView) findViewById(R.id.attachElistView);
		mListView=(ListView)findViewById(R.id.attachListView);
		detail_loading = (ProgressBar)findViewById(R.id.detail_loading);
		mAttachText = (TextView)findViewById(R.id.attachinfo);
		mShare = (ImageView) findViewById(R.id.share);
		mAttachText = (TextView)findViewById(R.id.attachinfo);
		if(null!=mItem&&mItem.getName()!=null){
			setTitle(mItem.getName());
		}
		mExpandableAdapter = new AdapterAttachExpandable(mContext, mPDAttachList);
		mExpandableListView.setAdapter(mExpandableAdapter);
		mLVAdapter = new AdapterAttach(mContext, mPDAttachList);
		mListView.setAdapter(mLVAdapter);
	}
	
	@Override
	protected void initWidgetEvent() {
		super.initWidgetEvent();
		mShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer stringBuffer=new StringBuffer();
				if(!StringUtil.isEmpty(mAttachText.getText().toString())){
					stringBuffer.append(mAttachText.getText().toString());	
				}
				if(null!=mPDAttachList){
					for(int k=0;k<mPDAttachList.size();k++){
							if(null!=mPDAttachList.get(k).getIntroduce()){
								stringBuffer.append(mPDAttachList.get(k).getIntroduce());	
							}
							if(null!=mPDAttachList.get(k).getTitle()){
								stringBuffer.append(mPDAttachList.get(k).getTitle());
							}
							if(null!=mPDAttachList.get(k).getContent()){
								stringBuffer.append(mPDAttachList.get(k).getContent());
							}
							if(mPDAttachList.get(k).getAttachOverrides()!=null){
								for(int s=0;s<mPDAttachList.get(k).getAttachOverrides().size();s++){
									if(null!=mPDAttachList.get(k).getAttachOverrides().get(s).getContent()){
										stringBuffer.append(mPDAttachList.get(k).getAttachOverrides().get(s).getContent());
									}
									stringBuffer.append(mPDAttachList.get(k).getAttachOverrides().get(s).getContent());
								}
							
						}
						mPDAttachList.get(k).getTitle();
					}
				}
				Intent intent=new Intent(Intent.ACTION_SEND); 
				intent.setType("text/plain"); // 纯文本  
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
				intent.putExtra(Intent.EXTRA_TEXT, stringBuffer.toString());  
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(Intent.createChooser(intent,mItem.getName())); 
			}
		});
		mExpandableListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(mSharedPreferences.getBoolean("expandable", false)){
					mSharedPreferences.edit().putBoolean("expandable", false).commit();
					for(int e=0;e<mPDAttachList.size();e++){
						mExpandableListView.collapseGroup(e);
					}
				}else{
					mSharedPreferences.edit().putBoolean("expandable", true).commit();
					if(null!=mPDAttachList){
						for(int e=0;e<mPDAttachList.size();e++){
							mExpandableListView.expandGroup(e);
						}
					}
				}
				return false;
			}
		});
		getData();
	};
	
	protected void getData() {
		// TODO Auto-generated method stub
		if(mItem!=null){
			if(null!= mDB.getAttachData(mItem)){
				for(int d=0;d<mDB.getAttachData(mItem).size();d++){
					if(mDB.getAttachData(mItem).get(d).isbListView()){
						bLV=true;
						continue;
					}
					if(mDB.getAttachData(mItem).get(d).getIntroduce()!=null){
						mAttachText.setVisibility(View.VISIBLE);
						mAttachText.setText(mDB.getAttachData(mItem).get(d).getIntroduce());
					}else{
						mPDAttachList.add(mDB.getAttachData(mItem).get(d));
					}
				}
			}
		}
		if(bLV){
			sendMessage(LVVISITITY);
		}else{
			sendMessage(EXPANDABLEVISITITY);
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
       		mItem=(Item) data.getExtras().getSerializable("item");
       		if(null!=mItem&&mItem.getName()!=null){
    			setTitle(mItem.getName());
    		}
       		mPDAttachList.clear();
       		if(bLV){
       			mLVAdapter.notifyDataSetChanged();
    		}else{
    			mExpandableAdapter.notifyDataSetChanged();
    		}
       		bLV=false;
       		mAttachText.setVisibility(View.GONE);
       		getData();
   		}
       }  
       super.onActivityResult(requestCode, resultCode, data);  
   }  
	
	public boolean handleMessage(Message msg) {
		mAttachText.setFocusable(true);
   		mAttachText.setFocusableInTouchMode(true);
		switch (msg.what) {
		case EXPANDABLEVISITITY:
			detail_loading.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
			mExpandableListView.setVisibility(View.VISIBLE);
			if(null==mPDAttachList||mPDAttachList.size()==0){
				if(StringUtil.isEmpty(mAttachText.getText().toString())){
					Toast.makeText(mContext,"没有数据！", Toast.LENGTH_SHORT).show();
				}
				return false;
			}
			boolean bExpandab=mSharedPreferences.getBoolean("expandable", false);
			if(null!=mPDAttachList&&bExpandab){
				for(int e=0;e<mPDAttachList.size();e++){
					mExpandableListView.expandGroup(e);
				}
			}
			mExpandableAdapter.notifyDataSetChanged();
			break;
		case LVVISITITY:
			detail_loading.setVisibility(View.GONE);
			mExpandableListView.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			if(null==mPDAttachList||mPDAttachList.size()==0){
				if(StringUtil.isEmpty(mAttachText.getText().toString())){
					Toast.makeText(mContext,"没有数据！", Toast.LENGTH_SHORT).show();
				}
			}
			mLVAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return super.handleMessage(msg);
	};

}
