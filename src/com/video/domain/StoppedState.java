package com.video.domain;

public class StoppedState extends PlayerState {

	public final VideoState state = VideoState.STOPPED;
	
	StoppedState(Video video) {
		super(video);
		// TODO Auto-generated constructor stub
	}

	@Override
	public VideoState onStop() {
		return VideoState.STOPPED;
	}

	@Override
	public VideoState onPlay() {
		video.changePlayerState(new PlayingState(video));
		return VideoState.PLAYING;
	}

	@Override
	public VideoState onPause() {
		return VideoState.STOPPED;
	}

}
