package com.video.domain;

public abstract class PlayerState {
	
	protected final Video video;
	
	protected VideoState state;

    /**
     * Context passes itself through the state constructor. This may help a
     * state to fetch some useful context data if needed.
     */
    PlayerState(Video video) {
        this.video = video;
    }

    public abstract VideoState onStop();
    public abstract VideoState onPlay();
    public abstract VideoState onPause();
    
    public enum VideoState {
    	STOPPED, PLAYING, PAUSED
    }
}
