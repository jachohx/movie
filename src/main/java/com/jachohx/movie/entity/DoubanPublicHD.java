package com.jachohx.movie.entity;

import java.util.ArrayList;
import java.util.List;

public class DoubanPublicHD{
	private DoubanMovie doubanMovie;
	private List<PublicHD> publicHDs = new ArrayList<PublicHD>();
	
	public DoubanMovie getDoubanMovie() {
		return doubanMovie;
	}
	public void setDoubanMovie(DoubanMovie doubanMovie) {
		this.doubanMovie = doubanMovie;
	}
	public List<PublicHD> getPublicHDs() {
		return publicHDs;
	}
	public void addPublicHD(PublicHD publicHD) {
		this.publicHDs.add(publicHD);
	}

}
