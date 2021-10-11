package com.video.domain;

public class PlayingState extends PlayerState {
	
	public final VideoState state = VideoState.PLAYING;

	PlayingState(Video video) {
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
		return VideoState.PLAYING;
	}

	@Override
	public VideoState onPause() {
		video.changePlayerState(new PausedState(video));
		return VideoState.PAUSED;
	}

}
