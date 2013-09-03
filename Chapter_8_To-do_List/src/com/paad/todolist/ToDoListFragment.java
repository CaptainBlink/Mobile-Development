package com.paad.todolist;

import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TwoLineListItem;

@SuppressWarnings("deprecation")
public class ToDoListFragment extends ListFragment {

	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TwoLineListItem twoLineListItem = (TwoLineListItem) view;

				String s = "------------------";
				s += "Position: " + position;
				s += "\nId: " + id;
				s += "\nText1: " + twoLineListItem.getText1().getText();
				s += "\nText2: " + twoLineListItem.getText2().getText();
				Log.d("onItemClick", s);
			}
		});
	}
}
