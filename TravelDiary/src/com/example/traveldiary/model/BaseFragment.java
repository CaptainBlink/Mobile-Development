package com.example.traveldiary.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	static TabLayoutActivity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = (TabLayoutActivity) this.getActivity();
	}

	public void onBackPressed() {
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}
}