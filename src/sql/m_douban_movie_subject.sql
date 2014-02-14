CREATE TABLE `m_douban_movie_subject` (
  `id` int(11) NOT NULL COMMENT '条目id',
  `title` varchar(50) NOT NULL COMMENT '中文名',
  `original_title` varchar(100) DEFAULT '0' COMMENT '原名',
  `alt` varchar(100) NOT NULL COMMENT '条目URL',
  `image_small` varchar(100) DEFAULT NULL COMMENT '电影海报图 288px x 465px',
  `image_medium` varchar(100) DEFAULT NULL COMMENT '电影海报图 96px x 155px',
  `image_large` varchar(100) DEFAULT NULL COMMENT '电影海报图 64px x 103p',
  `rating` int(1) DEFAULT 0 COMMENT '评分',
  `pubdates` varchar(50) DEFAULT NULL COMMENT '如果条目类型是电影则为上映日期，如果是电视剧则为首播日期',
  `year` int(4) DEFAULT 0 COMMENT '年代',
  `subtype` varchar(20) DEFAULT 'movie' COMMENT '条目分类, movie或者tv',
  `casts` varchar(100) DEFAULT NULL COMMENT '演员ID',
  `directors` varchar(100) DEFAULT NULL COMMENT '导演ID',
  `aka` varchar(200) DEFAULT NULL COMMENT '又名',
  `summary` varchar(1000) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_year` (`title`,`year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;