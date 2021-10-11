package com.video.persistence.daos;

import java.util.Set;

import com.video.domain.Tag;

public interface TagDao {
	public boolean addTag(Tag tag);
	public Set<Tag> getAllTags();
	public Tag getTag(String word);
}
