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
     * ������ҳ��ť
     */
	private Button gomainmenu;
	 /**
    * ����������ͣ����ʼ��ť
    */
	private Button author_music;
	 /**
    * ����������
    */
	private SoundService soundService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����Ϊ��Ļ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//���ز����ļ�
		setContentView(R.layout.author_activity);
		Log.v("ThAuthor", "onCreate()");
		
		//��ʼ����ť
		gomainmenu = (Button) this.findViewById(R.id.author_gomainmenu);
		author_music = (Button) this.findViewById(R.id.author_music);
		
		//���ð�ť����¼�
		gomainmenu.setOnClickListener(this);
		author_music.setOnClickListener(this);
		//��ʼ�����������ࣺ���Ű�ť��Ч���������߼�������
		soundService = new SoundService(AboutUSActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.author_gomainmenu:
			finish();
			break;

		case R.id.author_music:
			//�������߼������֣�������ŵ�ʱ�򣬵������ͣ��������Ȼ��
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
		//����������Ӱ��ֹͣ�����ͷ���Դ
		soundService.authorHometownMusicStop(R.raw.myhome);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		Log.v("ThAuthor", "onResume()");
		//�����ý����ʱ�򣬲������߼�������
		soundService.authorHometownMusicPlay(R.raw.myhome);
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		Log.v("ThAuthor", "onStop()");
		//��ȫ����ʾ��ʱ��������ֹͣ�����ͷ���Դ
		soundService.authorHometownMusicStop(R.raw.myhome);
		super.onStop();
	}

}