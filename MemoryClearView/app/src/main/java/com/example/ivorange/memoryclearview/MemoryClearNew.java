package com.example.ivorange.memoryclearview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ivorange on 15/12/12.
 */
public class MemoryClearNew extends View {

	private int mSceenWidth;
	private int mSceenHeight;
	private Point mCenterPoint;
	private int mOneRadius;
	private boolean mIsTouch;
	private Paint mCirclePaint;
	private Paint mArcPaint;
	private Paint mArcPaint1;
	private RectF mRectf;
	private TextView mTextTip;
	private FrameLayout mFramRoot;
	private int mTotalNum;
	private int mCurrentNum;
	private float mTempLevel;
	private float mCurrentLevel;
	private int mNewNum;
	private Timer mTimer;
	private boolean mIsUp;

	public MemoryClearNew(Context context) {
		this(context, null);
	}

	public MemoryClearNew(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MemoryClearNew(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	public void initData(){
		mIsTouch=false;
		mIsUp=false;
		mTimer=new Timer();
		mCirclePaint=new Paint();
		mCirclePaint.setDither(true);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(3);
		mArcPaint=new Paint();
		mArcPaint.setDither(true);
		mArcPaint.setStyle(Paint.Style.FILL);

		mArcPaint1=new Paint();
		mArcPaint1.setAlpha(20);
		mArcPaint1.setStyle(Paint.Style.FILL);
		mTotalNum=1000;
		mCurrentNum=800;
		mNewNum=500;
		mCurrentLevel=mCurrentNum*1.0f/mTotalNum*100;
		mTempLevel=mCurrentLevel;
	}

	public void beginDraw(){
		mCurrentLevel=mNewNum*1.0f/mTotalNum*100;
		if(mIsTouch){
			return;
		}else{
			postInvalidate();
			mIsTouch=true;
			beginAnimator();
		}
		TimerTask timerTask=new TimerTask() {
			@Override
			public void run() {
				if(!mIsUp){
					if(mTempLevel>0){
						mTempLevel-=0.4;
						postInvalidate();
					}else {
						mTempLevel=0;
						mIsUp=true;
						postInvalidate();
					}
				}else{
					if(mTempLevel<mCurrentLevel){
						mTempLevel+=0.4;
						postInvalidate();
					}else{
						mTempLevel=mCurrentLevel;
						this.cancel();
						mIsUp=false;

						postInvalidate();
					}
				}
			}
		};
		mTimer.schedule(timerTask,700,12);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(mIsTouch){
			if(mTempLevel<30){
				mCirclePaint.setColor(getResources().getColor(R.color.one_green));
				mArcPaint.setColor(getResources().getColor(R.color.one_green));
			}else if(mTempLevel>=30&&mTempLevel<=70){
				mCirclePaint.setColor(getResources().getColor(R.color.one_orange));
				mArcPaint.setColor(getResources().getColor(R.color.one_orange));
			}else if(mTempLevel>70){
				mCirclePaint.setColor(getResources().getColor(R.color.one_red));
				mArcPaint.setColor(getResources().getColor(R.color.one_red));
			}
			mCirclePaint.setAlpha(65);
			canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mOneRadius - 5, mCirclePaint);
			mCirclePaint.setAlpha(130);
			canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, (float) (mOneRadius - mOneRadius * 2
							/ 10),
					mCirclePaint);
			mCirclePaint.setAlpha(200);
			canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mOneRadius - mOneRadius * 2 / 5,
					mCirclePaint);
			canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mOneRadius - mOneRadius * 2 / 5, mArcPaint1);
			mRectf=new RectF(mCenterPoint.x-mOneRadius * 3 / 5,mCenterPoint.y-mOneRadius * 3 / 5,mCenterPoint.x+mOneRadius * 3 / 5,mCenterPoint.y+mOneRadius * 3 / 5);
			mArcPaint.setAlpha(200);
			canvas.drawArc(mRectf, 180, -90, true, mArcPaint);
			mTextTip.setText((int) mTempLevel + "%");
		}

	}

	public void beginAnimator(){
		ObjectAnimator animatorAlphaStart=ObjectAnimator.ofFloat(this, "alpha",0f,1f).setDuration(600);
		ObjectAnimator animatorrota=ObjectAnimator.ofFloat(this, "rotation", 0f, 8640f).setDuration(5000);
		ObjectAnimator animatorscaleX=ObjectAnimator.ofFloat(this, "scaleX", 0.5f, 2f).setDuration(600);
		ObjectAnimator animatorscaleY=ObjectAnimator.ofFloat(this, "scaleY", 0.5f, 2f).setDuration(600);
		ObjectAnimator animatorscaleXEnd=ObjectAnimator.ofFloat(this, "scaleX", 2f, 0.2f,2f).setDuration(400);
		ObjectAnimator animatorscaleYEnd=ObjectAnimator.ofFloat(this, "scaleY",2f, 0.2f,2f).setDuration(400);
		ObjectAnimator animatorAlpha=ObjectAnimator.ofFloat(this, "alpha",1f,0f).setDuration(700);
		animatorAlpha.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mIsTouch=false;
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		AnimatorSet set=new AnimatorSet();
		set.play(animatorscaleX).with(animatorscaleY).with(animatorAlphaStart);
		set.play(animatorrota).after(animatorscaleY);
		set.play(animatorscaleXEnd).with(animatorscaleYEnd).with(animatorAlpha).after(animatorrota);
		set.start();

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mSceenWidth=w;
		mSceenHeight=h;
		mCenterPoint=new Point(mSceenWidth/2,mSceenHeight/2);
		mOneRadius=(mSceenWidth>mSceenHeight?mSceenHeight:mSceenWidth)/4;
		mFramRoot=(FrameLayout)getParent();
		mTextTip=(TextView)mFramRoot.getChildAt(1);

	}
}
