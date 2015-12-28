package com.example.ivorange.memoryclearview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ivorange on 15/12/25.
 */
public class DemoBlurView extends View {

	private Paint mPaint;
	private int mSceenWidth;
	private int mSceenHeight;
	private Rect mRect;

	public DemoBlurView(Context context) {
		this(context,null);
	}

	public DemoBlurView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DemoBlurView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	public void initData(){
		mPaint=new Paint();
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setAlpha(60);
		mPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(mRect,mPaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mSceenWidth=w;
		mSceenHeight=h;
		mRect=new Rect(0,0,w,h);
	}
}
