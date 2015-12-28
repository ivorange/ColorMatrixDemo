package com.example.ivorange.memoryclearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ColorActivity extends Activity implements View.OnClickListener{


	private Button button = null;
	private ColorView colorView = null;
	private EditText[] editTextArray = null;
	private float colorArray[] = null;
	private int[] EditTextID = {R.id.Edit1,R.id.Edit2,R.id.Edit3,R.id.Edit4,R.id.Edit5,
			R.id.Edit6,R.id.Edit7,R.id.Edit8,R.id.Edit9,R.id.Edit10,
			R.id.Edit11,R.id.Edit12,R.id.Edit13,R.id.Edit14,R.id.Edit15,
			R.id.Edit16,R.id.Edit17,R.id.Edit18,R.id.Edit19,R.id.Edit20};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);
		button = (Button)findViewById(R.id.Button);
		button.setOnClickListener(this);
		editTextArray = new EditText[20];
		colorArray = new float[20];
		for(int i = 0;i < 20;i++){
			editTextArray[i] = (EditText)findViewById(EditTextID[i]);
		}

		colorView = (ColorView)findViewById(R.id.myColorView);
//		colorView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ttt);
//				bitmap=blurBitmap(bitmap);
//				colorView.setImageBitmap(bitmap);
//				colorView.invalidate();
//			}
//		});
	}

	public Bitmap blurBitmap(Bitmap bitmap){

		//Let's create an empty bitmap with the same size of the bitmap we want to blur
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		//Instantiate a new Renderscript
		RenderScript rs = RenderScript.create(getApplicationContext());

		//Create an Intrinsic Blur Script using the Renderscript
		ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

		//Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
		Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
		Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

		//Set the radius of the blur
		blurScript.setRadius(25);

		//Perform the Renderscript
		blurScript.setInput(allIn);
		blurScript.forEach(allOut);

		//Copy the final bitmap created by the out Allocation to the outBitmap
		allOut.copyTo(outBitmap);

		//recycle the original bitmap
		bitmap.recycle();

		//After finishing everything, we destroy the Renderscript.
		rs.destroy();

		return outBitmap;


	}

	@Override
	public void onClick(View v) {
		for(int i = 0;i < 20;i++){
			colorArray[i] = Float.valueOf(editTextArray[i].getText().toString().trim());
			System.out.println("i = " + i + ":" + editTextArray[i].getText().toString().trim());
		}
		colorView.setColorArray(colorArray);
	}
}
