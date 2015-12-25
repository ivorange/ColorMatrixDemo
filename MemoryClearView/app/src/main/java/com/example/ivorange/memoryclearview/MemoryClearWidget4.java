package com.example.ivorange.memoryclearview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ivorange on 15/12/7.
 */
public class MemoryClearWidget4 extends View {

	// y = Asin(wx+b)+h
	private static final float STRETCH_FACTOR_A = 10;
	private static final int OFFSET_Y = 0;
	// 第一条水波移动速度
	private static final int TRANSLATE_X_SPEED_ONE = 4;
	// 第二条水波移动速度
	private static final int TRANSLATE_X_SPEED_TWO = 2;
	private float mCycleFactorW;
	private CirclePoint mSceenPoint;
	private int mCircleRaduis;
	private Paint mCirclePaint;
	private int mTotalWidth, mTotalHeight;
	private float[] mYPositions;
	private float[] mResetOneYPositions;
	private float[] mResetTwoYPositions;
	private int mXOffsetSpeedOne;
	private int mXOffsetSpeedTwo;
	private int mXOneOffset;
	private int mXTwoOffset;
	private float mCurrentLevel=30;
	private int mCurrentHeight;
	private int mSceenHeight;
	private PorterDuffXfermode mPorterModel;
	private Timer mTimer;
	private Paint mWavePaint;
	private Paint mWavePainttwo;
	private Paint mTempPaint;
	private boolean mIsFull;
	private DrawFilter mDrawFilter;
	private boolean mIsTouch;
	private LinearGradient shader;
	private LinearGradient shader2;
	private Matrix mMar;

	public MemoryClearWidget4(Context context, AttributeSet attrs) {
		super(context, attrs);
		mXOffsetSpeedOne = dip2px(context, TRANSLATE_X_SPEED_ONE);
		mXOffsetSpeedTwo = dip2px(context, TRANSLATE_X_SPEED_TWO);
		mIsFull=false;
		mTimer=new Timer();
		mCirclePaint=new Paint();
		mCirclePaint.setColor(Color.GRAY);
		mPorterModel=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
		mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint
				.FILTER_BITMAP_FLAG);
		mIsTouch=false;
		initPaint();
		mMar=new Matrix();
	}

	public void initPaint(){
		mWavePaint = new Paint();
		mWavePaint.setAntiAlias(true);
		mWavePaint.setStyle(Paint.Style.FILL);
		mWavePaint.setDither(true);
		mWavePainttwo = new Paint();
		mWavePainttwo.setAntiAlias(true);
		mWavePainttwo.setStyle(Paint.Style.FILL);
		mWavePainttwo.setDither(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_UP:
			{
				if(mIsTouch){
					return true;
				}
				mIsTouch=true;
				TimerTask task=new TimerTask() {
					@Override
					public void run() {
						if(mCurrentLevel<100&&!mIsFull){
							mCurrentLevel+=0.4;
							postInvalidate();
						}else if(mCurrentLevel>=100){
							mIsFull=true;
							mCurrentLevel-=0.4;
							postInvalidate();
						}else if(mCurrentLevel<=100&&mIsFull){
							mCurrentLevel-=0.4;
							postInvalidate();
							if(mCurrentLevel<=20){
								this.cancel();
								mIsFull=false;
								mIsTouch=false;
								postInvalidate();
							}
						}
					}
				};
				mTimer.schedule(task,0,8);
			}
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mSceenHeight = getHeight();
		mCurrentHeight=(int)(mCurrentLevel/100*mSceenHeight);
		mTotalWidth = getWidth();
		mTotalHeight = getHeight();
		mSceenPoint=new CirclePoint(mTotalWidth/2,mTotalHeight/2);
		mCircleRaduis=(mTotalWidth>mTotalHeight?mTotalWidth:mTotalHeight)/2;
		mYPositions = new float[mTotalWidth];
		mResetOneYPositions = new float[mTotalWidth];
		mResetTwoYPositions = new float[mTotalWidth];
		if(mIsTouch){
			mWavePaint.setShader(shader);
			mWavePainttwo.setShader(shader2);
			mMar.setTranslate(0, mCurrentLevel * 6);
			shader.setLocalMatrix(mMar);
			shader2.setLocalMatrix(mMar);
		}else {
			initPaint();
			if(mCurrentLevel<=30){
				mWavePaint.setColor(getResources().getColor(R.color.one_green));
				mWavePainttwo.setColor(getResources().getColor(R.color.two_green));

			}else if(mCurrentLevel>30&&mCurrentLevel<=80){
				mWavePaint.setColor(getResources().getColor(R.color.one_orange));
				mWavePainttwo.setColor(getResources().getColor(R.color.two_orange));

			}else if(mCurrentLevel>80&&mCurrentLevel<=100){
				mWavePaint.setColor(getResources().getColor(R.color.one_red));
				mWavePainttwo.setColor(getResources().getColor(R.color.two_red));

			}
		}

		canvas.save();
		Path path=new Path();
		path.addCircle(mSceenPoint.pointX, mSceenPoint.pointY, mCircleRaduis, Path
				.Direction.CCW);
		canvas.clipPath(path, Region.Op.REPLACE);

		mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

		for (int i = 0; i < mTotalWidth; i++) {
			mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
		}

		canvas.setDrawFilter(mDrawFilter);
		resetPositonY();
		for (int i = 0; i < mTotalWidth; i++) {

			canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - mCurrentHeight, i,
					mTotalHeight,
					mWavePaint);

			canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - mCurrentHeight, i,
					mTotalHeight,
					mWavePainttwo);
		}

		String tip=(int)mCurrentLevel+"%";
		Paint textPaint=new Paint();
		textPaint.setTextSize(40);
		textPaint.setColor(Color.WHITE);
		int x_length=(int)textPaint.measureText(tip);
		canvas.drawText(tip,mSceenPoint.pointX-x_length/2,mSceenPoint.pointY,textPaint);

		mXOneOffset += mXOffsetSpeedOne;
		mXTwoOffset += mXOffsetSpeedTwo;

		// 如果已经移动到结尾处，则重头记录
		if (mXOneOffset >= mTotalWidth) {
			mXOneOffset = 0;
		}
		if (mXTwoOffset > mTotalWidth) {
			mXTwoOffset = 0;
		}
		canvas.restore();

	}



	private void resetPositonY() {
		// mXOneOffset代表当前第一条水波纹要移动的距离
		int yOneInterval = mYPositions.length - mXOneOffset;
		// 使用System.arraycopy方式重新填充第一条波纹的数据
		System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
		System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

		int yTwoInterval = mYPositions.length - mXTwoOffset;
		System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
				yTwoInterval);
		System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 记录下view的宽高
		mTotalWidth = w;
		mTotalHeight = h;
		mSceenPoint=new CirclePoint(mTotalWidth/2,mTotalHeight/2);
		mCircleRaduis=(mTotalWidth>mTotalHeight?mTotalWidth:mTotalHeight)/2;
		mYPositions = new float[mTotalWidth];
		// 用于保存波纹一的y值
		mResetOneYPositions = new float[mTotalWidth];
		// 用于保存波纹二的y值
		mResetTwoYPositions = new float[mTotalWidth];

		// 将周期定为view总宽度
		mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

		// 根据view总宽度得出所有对应的y值
		for (int i = 0; i < mTotalWidth; i++) {
			mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
		}
		shader= new LinearGradient(0, 0, 0, -2*mTotalHeight, new int[]{getResources().getColor(R.color.one_green), getResources().getColor(R.color.one_orange), getResources().getColor(R.color.one_red)},new float[]{0,0.2f,1f}, Shader.TileMode.CLAMP);
		shader2= new LinearGradient(0, 0, 0, -2*mTotalHeight, new int[]{getResources().getColor(R.color.two_green),getResources().getColor(R.color.two_orange), getResources().getColor(R.color.two_red)},new float[]{0,0.2f,1f}, Shader.TileMode.CLAMP);

	}


	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public class CirclePoint{
		public int pointX;
		public int pointY;

		public CirclePoint(int x,int y){
			this.pointX=x;
			this.pointY=y;
		};
	}
}
