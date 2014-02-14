CREATE TABLE `m_publichd_douban` (
  `phd_id` int(11) NOT NULL COMMENT 'publichd id',
  `douban_id` int(11) DEFAULT -1 COMMENT 'douban id。0匹配不上',
  `name` varchar(100) NOT NULL COMMENT '电影名',
  `year` int(4) DEFAULT 0 COMMENT '年代',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`phd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'PublicHD与豆瓣电影对照表';