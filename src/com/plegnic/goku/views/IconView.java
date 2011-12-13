package com.plegnic.goku.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plegnic.goku.R;

public class IconView extends RelativeLayout{
	ImageView thumb;
	TextView label;

	public IconView(Context context) {
		super(context);
		
		LayoutInflater.from(context).inflate(R.layout.icon, this, true);
		
		thumb = (ImageView) findViewById(R.id.image);
		thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);

		label = (TextView) findViewById(R.id.label);
		label.setGravity(Gravity.CENTER);
		label.setPadding(2, 2, 2, 2);
		label.setTextColor(Color.WHITE);
		
	}
	
	public void setImageResource(int resource) {
		thumb.setImageResource(resource);
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		thumb.setImageBitmap(bitmap);
	}
	
	public void setText(String text) {
		label.setText(text);
	}
}
