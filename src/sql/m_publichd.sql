CREATE TABLE `m_publichd` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(200) NOT NULL COMMENT 'url',
  `md5` varchar(32) NOT NULL COMMENT 'url md5',
  `category` int(11) DEFAULT 0 COMMENT '分类ID',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `magnet` varchar(500) DEFAULT NULL COMMENT 'magnet下载地址',
  `torrent` varchar(500) DEFAULT NULL COMMENT 'torrent下载地址',
  `add_date` int(8) DEFAULT '0' COMMENT '增加日期',
  `seeds` int(8) DEFAULT '0' COMMENT '种子数',
  `leechers` int(8) DEFAULT '0' COMMENT '预下载者数量',
  `uploader` varchar(50) DEFAULT NULL COMMENT '上传者',
  `size` varchar(20) DEFAULT NULL COMMENT '文件大小',
  `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `publichd_md5` (`md5`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;