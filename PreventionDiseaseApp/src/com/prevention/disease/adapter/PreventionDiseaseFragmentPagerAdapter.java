package com.prevention.disease.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class PreventionDiseaseFragmentPagerAdapter extends FragmentPagerAdapter {

	FragmentManager fm;
	ArrayList<Fragment> fragments;

	public PreventionDiseaseFragmentPagerAdapter(
			FragmentManager supportFragmentManager,
			ArrayList<Fragment> fragments) {
		// TODO Auto-generated constructor stub
		super(supportFragmentManager);
		this.fragments = fragments;
		this.fm = supportFragmentManager;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(ArrayList<Fragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fragments.clear();
		}
		notifyDataSetChanged();
	}

}
