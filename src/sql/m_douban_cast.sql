CREATE TABLE `m_douban_cast` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '条目id',
  `name` varchar(50) NOT NULL COMMENT '中文名',
  `alt` varchar(100) NOT NULL COMMENT '影人条目URL',
  `avatar_small` varchar(100) DEFAULT NULL COMMENT '影人头像 420px x 600px',
  `avatar_medium` varchar(100) DEFAULT NULL COMMENT '影人头像 140px x 200px',
  `avatar_large` varchar(100) DEFAULT NULL COMMENT '影人头像 70px x 100px',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '演员表';