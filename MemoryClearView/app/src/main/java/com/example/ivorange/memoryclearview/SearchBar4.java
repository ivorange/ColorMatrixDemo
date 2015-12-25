package com.example.ivorange.memoryclearview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by ivorange on 15/12/21.
 */
public class SearchBar4 extends LinearLayout {

	public SearchBar4(Context context) {
		this(context,null);
	}

	public SearchBar4(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SearchBar4(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public void initView(){
		LayoutInflater.from(getContext()).inflate(R.layout.searchbar4,this,true);
	}

}
