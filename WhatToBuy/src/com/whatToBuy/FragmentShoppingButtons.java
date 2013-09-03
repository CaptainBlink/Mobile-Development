package com.whatToBuy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentShoppingButtons extends Fragment {

	Service service = Service.getInstance();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create, or inflate the Fragment’s UI, and return it.
		// If this Fragment has no UI then return null.
		return inflater.inflate(R.layout.fragment_shopping, container, false);

	}

}
