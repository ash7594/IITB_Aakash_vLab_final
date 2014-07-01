package com.virtualis.exp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.virtualis.R;

public class Videos extends Activity {
	
	int cardRest;
	String userRest;
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	
	private ViewFlipper viewFlipper;
	private Button btn1,btn2;
	String VideoUrls;
	String[] Urls = null;
	int no_vid = 0;
	TextView label;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_videos);
		
		VideoUrls = getIntent().getExtras().getString("video_urls");
		Urls = VideoUrls.split(",");
		
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		
		label = (TextView) findViewById(R.id.video_no);
		label.setText("Video 1");
		for (int j = 0; j < Urls.length; j++) {
			
			
			
			final VideoView video = new VideoView(this);
			Uri uri=Uri.parse(Urls[j]);
		    video.setVideoURI(uri);
		    MediaController mc = new MediaController(this);
		    video.setMediaController(mc);
		    video.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT  , LayoutParams.MATCH_PARENT ));
			video.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					Toast.makeText(Videos.this, "Video URL Unreachable ", Toast.LENGTH_SHORT).show();
					return true;
				}
			});
			
			
			//label.setText(Html.fromHtml("Video " + (j+1)));
			//label.setPadding(10, 10, 10, 10);
			//label.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));
			
			
			LinearLayout mLinearLayout = new LinearLayout(this);
			mLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			mLinearLayout.setOrientation(1);
			LinearLayout mLinearLayout2 = new LinearLayout(this);
			
			mLinearLayout2.setOrientation(0);
			mLinearLayout.addView(mLinearLayout2);
			LinearLayout mLinearLayout3 = new LinearLayout(this);
			
			mLinearLayout3.setOrientation(1);
			mLinearLayout2.addView(mLinearLayout3);
			//mLinearLayout3.addView(label);
			mLinearLayout.addView(video);
			mLinearLayout.setTag(""+(j+1));
			viewFlipper.addView(mLinearLayout);
			
			btn1 = (Button) findViewById(R.id.prev_btn);
			btn2 = (Button) findViewById(R.id.next_btn);
			
			btn1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					video.stopPlayback();
					viewFlipper.showPrevious();
					label.setText("Video "+ (String)viewFlipper.getCurrentView().getTag());
				}
			});
			
			btn2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					video.stopPlayback();
					viewFlipper.showNext();
					

					label.setText("Video "+ (String)viewFlipper.getCurrentView().getTag());
					//Toast.makeText(getApplication(), ""+viewFlipper.getCurrentView().getTag(), Toast.LENGTH_SHORT).show();
				}
			});
			
			gestureDetector = new GestureDetector(getApplicationContext(),new MyGestureDetector());
			gestureListener = new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
						if (gestureDetector.onTouchEvent(event)) {
							return true;
						}
					return false;
				}
			};
			
		}
}

class MyGestureDetector extends SimpleOnGestureListener {
	
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(Videos.this, R.animator.one_in_from_right);
                    viewFlipper.setOutAnimation(Videos.this, R.animator.one_out_to_left);
					viewFlipper.showNext();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(Videos.this, R.animator.one_in_from_left);
                    viewFlipper.setOutAnimation(Videos.this, R.animator.one_out_to_right);
					viewFlipper.showPrevious();
				}
			} catch (Exception e) {}
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event))
			return true;
		else
			return false;
	}
}

