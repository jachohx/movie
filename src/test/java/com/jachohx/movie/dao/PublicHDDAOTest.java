package com.jachohx.movie.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jachohx.movie.entity.PublicHD;
import com.jachohx.movie.util.SpringUtils;

public class PublicHDDAOTest {
	PublicHDDAO dao;
	@Before
    public void setUp() {
		dao = (PublicHDDAO)SpringUtils.getInstance().getBean("publicHDDAO");
	}
	@After
    public void tearDown(){
    }
//	@Test
	public void insert(){
		PublicHD entity = new PublicHD();
		entity.setCategory(2);
		entity.setName("The Kingdom 2007 Hybrid 720p BluRay DTS x264 NiP [PublicHD]");
		entity.setUrl("index.php?page=torrent-details&id=8ae291d187ea6d99fc81960c4f2ac2e33016ab93");
		entity.setMd5("2c7dfc5313d6c6ebe8ea50c13d44b4e2");
		entity.setMagnet("magnet:?xt=urn:btih:RLRJDUMH5JWZT7EBSYGE6KWC4MYBNK4T&dn=The.Kingdom.2007.Hybrid.720p.BluRay.DTS.x264-NiP+[PublicHD]&tr=udp://tracker.publichd.eu/announce&tr=udp://tracker.1337x.org:80/announce&tr=udp://tracker.openbittorrent.com:80/announce&tr=http://fr33dom.h33t.com:3310/announce");
		entity.setTorrent("http://istoretor.com/t/8ae291d187ea6d99fc81960c4f2ac2e33016ab93/The.Kingdom.2007.Hybrid.720p.BluRay.DTS.x264-NiP+%5BPublicHD%5D.torrent");
		entity.setAddDate(20131222);
		entity.setSeeds(29);
		entity.setLeechers(54);
		entity.setUploader("aoloffline");
		entity.setSize("7.94 GB");
		dao.insert(entity);
	}
	
	@Test
	public void getPublicHD4Md5() {
		PublicHD entity = dao.getPublicHD4Md5("2c7dfc5313d6c6ebe8ea50c13d44b4e2");
		if (entity == null) {
			System.out.println("null");
			return;
		}
		System.out.println(entity.getId());
	}
}
