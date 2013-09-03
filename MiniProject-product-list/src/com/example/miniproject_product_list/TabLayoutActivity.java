package com.example.miniproject_product_list;

import java.util.HashMap;
import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

public class TabLayoutActivity extends FragmentActivity {
	private TabHost mTabHost;
	private HashMap<String, Stack<Fragment>> mStacks;
	private String mCurrentTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);

		mStacks = new HashMap<String, Stack<Fragment>>();
		mStacks.put(AppConstants.TAB_A, new Stack<Fragment>());
		mStacks.put(AppConstants.TAB_B, new Stack<Fragment>());

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();

		initializeTabs();
	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(null, null);
		// ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		// imageView.setImageDrawable(getResources().getDrawable(id));
		return view;
	}

	public void initializeTabs() {
		/* Setup your tab icons and content views.. Nothing special in this.. */
		TabHost.TabSpec spec = mTabHost.newTabSpec(AppConstants.TAB_A);
		mTabHost.setCurrentTab(-3);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator("Products");
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(AppConstants.TAB_B);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator("Shopping List");
		mTabHost.addTab(spec);
	}

	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {
			mCurrentTab = tabId;

			if (mStacks.get(tabId).size() == 0) {
				if (tabId.equals(AppConstants.TAB_A)) {
					pushFragments(tabId, new ProductListFragment(), false, true);
				} else if (tabId.equals(AppConstants.TAB_B)) {
					pushFragments(tabId, new CartFragment(), false, true);
				}

			} else {
				pushFragments(tabId, mStacks.get(tabId).lastElement(), false,
						false);
			}
		}
	};

	public void setCurrentTab(int val) {
		mTabHost.setCurrentTab(val);
	}

	public void pushFragments(String tag, Fragment fragment,
			boolean shouldAnimate, boolean shouldAdd) {
		if (shouldAdd)
			mStacks.get(tag).push(fragment);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	public void popFragments() {
		Fragment fragment = mStacks.get(mCurrentTab).elementAt(
				mStacks.get(mCurrentTab).size() - 2);

		mStacks.get(mCurrentTab).pop();

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		// ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		if (mStacks.get(mCurrentTab).size() == 1) {
			finish();
			return;
		}

		((BaseFragment) mStacks.get(mCurrentTab).lastElement()).onBackPressed();

		popFragments();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (mStacks.get(mCurrentTab).size() == 0) {
			return;
		}

		/* Now current fragment on screen gets onActivityResult callback.. */
		mStacks.get(mCurrentTab).lastElement()
				.onActivityResult(requestCode, resultCode, data);
	}
	

	 
	 
}
