package com.virtualis.exp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.virtualis.R;

@SuppressLint("ValidFragment")
public class Video_Frag extends Fragment{

	VideoView videoView;
	String videoFile;
	
	public Video_Frag(){
		
	}
	
	public Video_Frag(String path){
		this.videoFile = path;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.one_video_frag,container,false);
		videoView = (VideoView)rootView.findViewById(R.id.videoView);
		
		MediaController mc=new MediaController(rootView.getContext());
		mc.setAnchorView(videoView);
		mc.setMediaPlayer(videoView);
		
		//Toast.makeText(rootView.getContext(),videoFile,Toast.LENGTH_LONG).show();
		videoView.setMediaController(mc);
		videoView.setVideoURI(Uri.parse(videoFile));
		videoView.start();
		videoView.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				Log.d("Error in Video playing",""+what);
				return true;
			}
		});
		return rootView;
		
	}
	
}