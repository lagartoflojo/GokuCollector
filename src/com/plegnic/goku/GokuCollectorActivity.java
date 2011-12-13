package com.plegnic.goku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.plegnic.goku.views.IconAdapter;

public class GokuCollectorActivity extends Activity {
	

	// Make strings for logging
	private final String TAG = this.getClass().getSimpleName();
	private final String RESTORE = ", can restore state";

	// The string "fortytwo" is used as an example of state
	private final String state = "fortytwo";

	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.collection_list);
		
		GridView collectionsGridView = (GridView) findViewById(R.id.CollectionsGridView);
		collectionsGridView.setAdapter(new IconAdapter(this));
		
		collectionsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(GokuCollectorActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
		});
		
		String answer = null;
		// savedState could be null
		if (null != savedState) {
			answer = savedState.getString("answer");
		}
		Log.i(TAG, "onCreate"
				+ (null == savedState ? "" : (RESTORE + " " + answer)));
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// Notification that the activity will be started
		Log.i(TAG, "onRestart");
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Notification that the activity is starting
		Log.i(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Notification that the activity will interact with the user
		Log.i(TAG, "onResume");
	}

	protected void onPause() {
		super.onPause();
		// Notification that the activity will stop interacting with the user
		Log.i(TAG, "onPause" + (isFinishing() ? " Finishing" : ""));
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Notification that the activity is no longer visible
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Notification the activity will be destroyed
		Log.i(TAG,
				"onDestroy "
						// Log which, if any, configuration changed
						+ Integer.toString(getChangingConfigurations(), 16));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Save instance-specific state
		outState.putString("answer", state);
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");

	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Log.i(TAG, "onRetainNonConfigurationInstance");
		// It's not what
		return new Integer(getTaskId());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		// Restore state; we know savedState is not null
		String answer = null != savedState ? savedState.getString("answer") : "";
		// This is a gratuitious test, remove it
		Object oldTaskObject = getLastNonConfigurationInstance();
		if (null != oldTaskObject) {
			int oldtask = ((Integer) oldTaskObject).intValue();
			int currentTask = getTaskId();
			// Task should not change across a configuration change
			assert oldtask == currentTask;
		}
		Log.i(TAG, "onRestoreInstanceState"
				+ (null == savedState ? "" : RESTORE) + " " + answer);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Get our option menu, infalte it, and call the superclass
		getMenuInflater().inflate(R.menu.goku_collector, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		boolean handled = false;
		switch (item.getItemId()) {
		default:
			return handled;
		}
	}
	
	
	/*
	private static final int DOT_DIAMETER = 6;
	private static final int CLEAR_MENU_ID = 1;
	private final Random rand = new Random();
	
	private static final class TrackingTouchListener implements View.OnTouchListener {
		
		private final Dots mDots;
		
		TrackingTouchListener(Dots dots) {
			mDots = dots;
		}

		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
				
			case MotionEvent.ACTION_MOVE:
				for(int i = 0, n = event.getHistorySize(); i < n; i++) {
					addDot(mDots, event.getHistoricalX(i), event.getHistoricalY(i),
							event.getHistoricalPressure(i), event.getHistoricalSize(i));
				}
				break;
				
			default:
				return false;
				
			}
			
			addDot(mDots, event.getX(), event.getY(), event.getPressure(), event.getSize());
			
			return true;
		}
		
		private void addDot(Dots dots, float x, float y, float p, float s) {
			dots.addDot(x, y, Color.CYAN, (int) ((p*s*DOT_DIAMETER) + 1));
		}
		
	}
	
	private final class DotGenerator implements Runnable {
		
		final Dots dots;
		final DotView view;
		final int color;
		private volatile boolean done;
		
		
		private final Handler handler = new Handler();
		private final Runnable makeDots = new Runnable() {
			
			public void run() {
				makeDot(dots, view, color);
			}
		};
		
		DotGenerator(Dots dots, DotView view, int color) {
			this.dots = dots;
			this.view = view;
			this.color = color;
		}
		
		public void done() {
			done = true;
		}

		public void run() {
			while(!done) {
				try { Thread.sleep(1000); }
				catch(Exception e) {}
				handler.post(makeDots);
			}
		}
	}
	
	
	final Dots dotModel = new Dots();
	DotView dotView;
	DotGenerator dotGenerator;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dotView = new DotView(this, dotModel);
        dotView.setLayoutParams(
        		new LinearLayout.LayoutParams(
        				ViewGroup.LayoutParams.FILL_PARENT,
        				ViewGroup.LayoutParams.WRAP_CONTENT,
        				1.0f
        		));
        setContentView(R.layout.main);
        
        dotView.setOnTouchListener(new TrackingTouchListener(dotModel));
        
        
        ((LinearLayout) findViewById(R.id.dotViewPlaceholder)).addView(dotView, 0);
        
        final EditText tb1 = (EditText) findViewById(R.id.editText1);
        final EditText tb2 = (EditText) findViewById(R.id.editText2);
        final Button startStop = (Button) findViewById(R.id.button3);
        
		startStop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				if(dotGenerator != null) {
					dotGenerator.done();
					dotGenerator = null;
					startStop.setText("Start");
					
				} else {
					dotGenerator = new DotGenerator(dotModel, dotView, Color.MAGENTA);
					new Thread(dotGenerator).start();
					startStop.setText("Stop");
				}
				
				
			}
		});
		
		dotModel.setDotsChangeListener(new Dots.DotsChangeListener() {
			
			public void onDotsChange(Dots dots) {
				Dot d = dots.getLastDot();
				tb1.setText((d == null) ? "" : String.valueOf(d.getX()));
				tb2.setText((d == null) ? "" : String.valueOf(d.getY()));
				dotView.invalidate();
			}
		});
    }

	protected void makeDot(Dots dots, DotView view, int color) {
		int pad = (DOT_DIAMETER+2)*2;
		dots.addDot(
				DOT_DIAMETER + (rand.nextFloat() * (view.getWidth() - pad)),
				DOT_DIAMETER + (rand.nextFloat() * (view.getHeight() - pad)),
				color, DOT_DIAMETER);
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(Menu.NONE, CLEAR_MENU_ID, Menu.NONE, "Clear");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case CLEAR_MENU_ID:
			dotModel.clearDots();
			return true;
		default: ;
		}
		
		return false;
		
	}*/
}