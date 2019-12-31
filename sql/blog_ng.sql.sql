/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.0.15-nt : Database - blog_ng
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog_ng` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `blog_ng`;

/*Table structure for table `t_blog` */

DROP TABLE IF EXISTS `t_blog`;

CREATE TABLE `t_blog` (
  `id` bigint(20) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `content` longtext,
  `first_picture` varchar(255) default NULL,
  `flag` varchar(255) default NULL,
  `views` int(11) default NULL,
  `appreciation` bit(1) default NULL,
  `share_statement` bit(1) default NULL,
  `commentabled` bit(1) default NULL,
  `published` bit(1) default NULL,
  `recommend` bit(1) default NULL,
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  `type_id` bigint(20) default NULL,
  `user_id` bigint(20) default NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK292449gwg5yf7ocdlmswv9w4j` (`type_id`),
  KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`user_id`),
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `t_blog` */

insert  into `t_blog`(`id`,`title`,`content`,`first_picture`,`flag`,`views`,`appreciation`,`share_statement`,`commentabled`,`published`,`recommend`,`create_time`,`update_time`,`type_id`,`user_id`,`description`) values (15,'ff','ff','https://unsplash.it/1000/800?image=1005','',0,'','','','','','2019-12-31 09:19:18','2019-12-31 09:19:18',1,1,NULL),(16,'fefe','fefe','https://unsplash.it/1000/800?image=1005','',4,'','','','','','2019-12-31 09:21:46','2019-12-31 09:21:46',1,1,NULL);

/*Table structure for table `t_blog_tags` */

DROP TABLE IF EXISTS `t_blog_tags`;

CREATE TABLE `t_blog_tags` (
  `blogs_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  KEY `FK5feau0gb4lq47fdb03uboswm8` (`tags_id`),
  KEY `FKh4pacwjwofrugxa9hpwaxg6mr` (`blogs_id`),
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`),
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `t_blog_tags` */

insert  into `t_blog_tags`(`blogs_id`,`tags_id`) values (15,1),(16,1);

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL auto_increment,
  `nickname` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `content` varchar(255) default NULL,
  `avatar` varchar(255) default NULL,
  `create_time` datetime default NULL,
  `blog_id` bigint(20) default NULL,
  `parent_comment_id` bigint(20) default NULL,
  `admin_comment` bit(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKke3uogd04j4jx316m1p51e05u` (`blog_id`),
  KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`),
  CONSTRAINT `FK4jj284r3pb7japogvo6h72q95` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_comment` (`id`),
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `t_comment` */

insert  into `t_comment`(`id`,`nickname`,`email`,`content`,`avatar`,`create_time`,`blog_id`,`parent_comment_id`,`admin_comment`) values (1,'小白','11@qq.com','wwwww','/images/avatar.png','2019-12-31 13:56:31',16,NULL,'\0'),(2,'小红','111@qq.comqq.com','斤斤计较','/images/avatar.png','2019-12-31 13:58:14',16,NULL,'\0'),(3,'小明','222@qq.com','二二二','/images/avatar.png','2019-12-31 14:20:04',16,1,'\0');

/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `t_tag` */

insert  into `t_tag`(`id`,`name`) values (1,'css'),(2,'javascript'),(3,'html'),(4,'java');

/*Table structure for table `t_type` */

DROP TABLE IF EXISTS `t_type`;

CREATE TABLE `t_type` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `t_type` */

insert  into `t_type`(`id`,`name`) values (1,'css'),(3,'javaScript'),(4,'ajax'),(6,'html');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `nickname` varchar(255) default NULL,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `avatar` varchar(255) default NULL,
  `type` int(11) default NULL,
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`nickname`,`username`,`password`,`email`,`avatar`,`type`,`create_time`,`update_time`) values (1,'曾元彬','zyb','96e79218965eb72c92a549dd5a330112','1536469005@qq.com','https://unsplash.it/450/450?image=1005',1,'2019-12-01 15:24:02','2019-12-27 15:24:09');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
