package com.plegnic.goku.views;

import com.plegnic.goku.models.Dot;
import com.plegnic.goku.models.Dots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class DotView extends View{
	
	private final Dots dots;
	
	public DotView(Context context, Dots dots) {
		super(context);
		this.dots = dots;
		setMinimumHeight(400);
		setMinimumWidth(400);
		setFocusable(true);
	}
	
	
	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(
			getSuggestedMinimumWidth(), 
			getSuggestedMinimumHeight());
	}
	
	@Override protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		
		Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setColor(hasFocus() ? Color.BLUE : Color.GRAY);
		canvas.drawRect(0, 0, getWidth()-1, getHeight()-1, paint);
		
		paint.setStyle(Style.FILL);
		
		for(Dot dot : dots.getDots()) {
			paint.setColor(dot.getColor());
			canvas.drawCircle(dot.getX(), dot.getY(), dot.getDiameter(), paint);
		}
	}

}
