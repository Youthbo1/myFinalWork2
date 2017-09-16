package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

public class MyGifView extends View {
	

	private long movieStart = 0;

	private Movie movie;

	public MyGifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		movie = Movie.decodeStream(context.getResources().openRawResource(R.raw.g1));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		long currentTime = System.currentTimeMillis();

		if (movieStart == 0) {
			movieStart = currentTime;
		}
		

		if (movie != null) {
			int duration = movie.duration();
			int relTime = (int) ((currentTime - movieStart) % duration);
			movie.setTime(relTime);
			movie.draw(canvas, 0, 0);

			invalidate();
		}
		
		

		super.onDraw(canvas);
	}
}
