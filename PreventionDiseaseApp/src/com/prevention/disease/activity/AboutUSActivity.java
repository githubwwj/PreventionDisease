package com.prevention.disease.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.prevention.disease.R;
import com.prevention.disease.service.SoundService;

public class AboutUSActivity extends Activity implements  OnClickListener {
    /**
     * 返回主页按钮
     */
	private Button gomainmenu;
	 /**
    * 声音播放暂停、开始按钮
    */
	private Button author_music;
	 /**
    * 声音服务类
    */
	private SoundService soundService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置为屏幕常亮
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//加载布局文件
		setContentView(R.layout.author_activity);
		Log.v("ThAuthor", "onCreate()");
		
		//初始化按钮
		gomainmenu = (Button) this.findViewById(R.id.author_gomainmenu);
		author_music = (Button) this.findViewById(R.id.author_music);
		
		//设置按钮点击事件
		gomainmenu.setOnClickListener(this);
		author_music.setOnClickListener(this);
		//初始化声音服务类：播放按钮音效、播放作者家乡音乐
		soundService = new SoundService(AboutUSActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.author_gomainmenu:
			finish();
			break;

		case R.id.author_music:
			//播放作者家乡音乐，如果播放的时候，点击将暂停，反正亦然；
			if (soundService.isAuthorHometownMusicPlaying(R.raw.myhome)) {
				soundService.authorHometownMusicPause(R.raw.myhome);
				author_music.setText(R.string.author_music_play);
			} else {
				soundService.authorHometownMusicPlay(R.raw.myhome);
				author_music.setText(R.string.author_music_pause);
			}

			break;
		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (soundService != null) {
			soundService.playButtonMusic(R.raw.button);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		//界面销毁是影音停止，并释放资源
		soundService.authorHometownMusicStop(R.raw.myhome);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		Log.v("ThAuthor", "onResume()");
		//界面获得焦点的时候，播放作者家乡音乐
		soundService.authorHometownMusicPlay(R.raw.myhome);
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		Log.v("ThAuthor", "onStop()");
		//完全不显示的时候是音乐停止，并释放资源
		soundService.authorHometownMusicStop(R.raw.myhome);
		super.onStop();
	}

}