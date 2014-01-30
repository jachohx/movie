package com.jachohx.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jachohx.movie.dao.PublicHDDAO;
import com.jachohx.movie.entity.PublicHD;

public class PublicHDService {
	@Autowired
	PublicHDDAO dao;
	
	public void setPublicHDDAO(PublicHDDAO dao) {
		this.dao = dao;
	}

	public List<PublicHD> listAll() {
		return dao.listAll();
	}
}
