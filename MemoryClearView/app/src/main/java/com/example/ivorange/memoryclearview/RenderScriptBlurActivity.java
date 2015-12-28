package com.example.ivorange.memoryclearview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ivorange on 15/12/27.
 */
public class RenderScriptBlurActivity extends FragmentActivity {

	private ImageView imageView;
	private TextView textViewTime;
	private ImageView textViewBlur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blur_view);
		imageView = (ImageView) findViewById(R.id.picture);
		textViewBlur = (ImageView) findViewById(R.id.img_blur);
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.square);
//		textViewTime = (TextView) findViewById(R.id.text_view_time);
		applyBlur();

	}

	public void applyBlur(){
		imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver
				.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				imageView.getViewTreeObserver().removeOnPreDrawListener(this);
				imageView.buildDrawingCache();
				Bitmap bmp = imageView.getDrawingCache();
				blur(bmp, textViewBlur);
				return true;
			}
		});
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void blur(Bitmap bmp, ImageView textViewBlur) {
		int radio = 20;
		Bitmap mask = Bitmap.createBitmap(textViewBlur.getMeasuredWidth(),
				textViewBlur.getMeasuredHeight(),
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(mask);
		canvas.translate(-textViewBlur.getLeft(),-textViewBlur.getTop());
		canvas.drawBitmap(bmp,0,0,null);
		Bitmap bitmap=BlurView.fastblurbyCaluate(getApplicationContext(), mask, radio);
		textViewBlur.setBackground(new BitmapDrawable(bitmap));
	}

	public static Bitmap ice(Bitmap bmp) {

		int width = bmp.getWidth();
		int height = bmp.getHeight();

		int dst[] = new int[width * height];
		bmp.getPixels(dst, 0, width, 0, 0, width, height);

		int R, G, B, pixel;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos]; // 获取图片当前点的像素值

				R = Color.red(pixColor); // 获取RGB三原色
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				pixel = R - G - B;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				R = pixel; // 计算后重置R值，以下类同

				pixel = G - B - R;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = B - R - G;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				dst[pos] = Color.rgb(R, G, B); // 重置当前点的像素值
			} // x
		} // y
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return bitmap;
	} // end of Ice[/mw_shl_code]
}


