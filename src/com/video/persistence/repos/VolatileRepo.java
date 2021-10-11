package com.video.persistence.repos;

import java.util.HashMap;
import java.util.Map;
import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.persistence.datasets.IMySet;
import com.video.persistence.datasets.MySet;

public class VolatileRepo implements IDataSource {

	private Map<Usuari, IMySet<Video>> videosUserMap = new HashMap<>();

	private IMySet<Video> allVideos = new MySet<>();

	private IMySet<Tag> allTags = new MySet<>();

	// SINGLETON
	private static VolatileRepo repo;

	private VolatileRepo() {
	}

	public static VolatileRepo getInstance() {
		if (repo == null)
			repo = new VolatileRepo();
		return repo;
	}

	// Getters
	@Override
	public Map<Usuari, IMySet<Video>> getVideosUserMap() {
		return videosUserMap;
	}

	@Override
	public IMySet<Video> getAllVideos() {
		return allVideos;
	}

	@Override
	public IMySet<Tag> getAllTags() {
		return allTags;
	}

	@Override
	public IMySet<Usuari> getAllUsers() {
		Map<Usuari, Usuari> map = new HashMap<>();
		for (Usuari u : videosUserMap.keySet()) {
			map.put(u, u);
		}
		return new MySet<Usuari>(map);
	}

	@Override
	public boolean add(Usuari user) {
		Object o = videosUserMap.get(user);
		boolean added = false;
		if (o == null) {
			videosUserMap.put(user, new MySet<Video>());
			added = true;
		}
		return added;
	}

	@Override
	public IMySet<Video> getAllVideos(Usuari user) {
		return videosUserMap.get(user);
	}

}
