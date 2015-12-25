package com.example.ivorange.memoryclearview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ivorange on 15/12/21.
 */
public class WeatherClock4 extends LinearLayout {

	private ImageView mImg0;
	private ImageView mImg1;
	private ImageView mImg2;
	private ImageView mImg3;
	private TextView widget_weather;
	private TextView widget_weather_clock_temperature; // 温度
	private ImageView widget_weather_clock_weather_icon;
	private ImageView widget_weather_clock_weather_default;
	private LinearLayout mllWeatherlayout;
	private LinearLayout mllClockTimeLocaltion;
	private ImageView[] mImageViews={mImg0,mImg1,mImg2,mImg3};
	private BitmapDrawable[] mArrBitmaps=new BitmapDrawable[10];

	public WeatherClock4(Context context) {
		this(context,null);
	}

	public WeatherClock4(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeatherClock4(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
		initView();
	}

	public void initView(){
		LayoutInflater.from(getContext()).inflate(R.layout.memory_clearview4, this, true);
		mImageViews[0]=(ImageView)findViewById(R.id.img_time0);
		mImageViews[1]=(ImageView)findViewById(R.id.img_time1);
		mImageViews[2]=(ImageView)findViewById(R.id.img_time2);
		mImageViews[3]=(ImageView)findViewById(R.id.img_time3);

	}



	public void initData(){
		for (int i=0;i<10;i++){
			int resId=getResources().getIdentifier("time_"+i,"drawable",getContext().getPackageName());
			BitmapDrawable bitmap=(BitmapDrawable)getResources().getDrawable(resId);
			mArrBitmaps[i]=bitmap;
		}

	}


}
