package com.video.domain;

public class PausedState extends PlayerState {

	public final VideoState state = VideoState.PAUSED;
	
	PausedState(Video video) {
		super(video);
		// TODO Auto-generated constructor stub
	}

	@Override
	public VideoState onStop() {
		video.changePlayerState(new StoppedState(video));
		return VideoState.STOPPED;
	}

	@Override
	public VideoState onPlay() {
		video.changePlayerState(new PlayingState(video));
		return VideoState.PLAYING;
	}

	@Override
	public VideoState onPause() {
		return VideoState.PAUSED;
	}

}
