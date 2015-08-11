# movie
抓取最新的高清电影(720p，1080P)下载地址，并从豆瓣电影得到电影信息

程序执行：
  
    org.jachohx.movie.run.MainRunner main/publichd/douban_movie/publichd_douban/daily_match 
    (这些参数可以在context.xml里的runnersMap的key查到)
  或者是执行每个key对应的bean，比如：
    
    com.jachohx.movie.run.PublicHDRunner
    com.jachohx.movie.run.DoubanMovieRunner
    com.jachohx.movie.run.PublicHDDoubanRunner
    com.jachohx.movie.run.DoubanPublicHDMatchRunner
  
  由于目前publichd.se已经关闭，爬取这个网站的数据已经不能执行了。
  所以publichd的那表的数据要手动插入才可以
