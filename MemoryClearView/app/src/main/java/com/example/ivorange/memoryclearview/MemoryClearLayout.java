package com.example.ivorange.memoryclearview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ivorange on 15/12/12.
 */
public class MemoryClearLayout extends LinearLayout {

	private TextView mTextTip;
	private MemoryClearNew mViewMemoryClear;
	private int mTotalNum;
	private int mCurrentNum;
	private int mCurrentLevel;
	private int mTempLevel;
	private boolean mIsTouch;


	public MemoryClearLayout(Context context) {
		this(context, null);
	}

	public MemoryClearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MemoryClearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public void initView(){
		LayoutInflater.from(getContext()).inflate(R.layout.memory_clear_view1,this,true);
		mTextTip=(TextView)findViewById(R.id.text_tip);
		mViewMemoryClear=(MemoryClearNew)findViewById(R.id.view_memoryclear);
		mTextTip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewMemoryClear.beginDraw();
			}
		});
	}

	public void beginDraw(){

	}
}
