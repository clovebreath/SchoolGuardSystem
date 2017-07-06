CREATE SCHEMA `schoolsys` ;
CREATE TABLE `schoolsys`.`student` (
  `sid` VARCHAR(20) NOT NULL,
  `sname` VARCHAR(20) NOT NULL,
  `spic` blob NOT NULL,
  `class` VARCHAR(20) NOT NULL,
  `tid` VARCHAR(20) NOT NULL,
  `sgender` ENUM('M', 'F') NOT NULL DEFAULT 'M',
  `canleave` ENUM('Y', 'N') NOT NULL DEFAULT 'N',
  `isschool` ENUM('Y', 'N') NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`sid`));
  CREATE TABLE `schoolsys`.`worker` (
  `wid` VARCHAR(20) NOT NULL,
  `wname` VARCHAR(20) NOT NULL,
  `wpic` blob NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  `position` ENUM('teacher', 'guard', 'director', 'assistant') NOT NULL default 'teacher',
  `isschool` ENUM('Y', 'N') NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`wid`));
  CREATE TABLE `schoolsys`.`parent` (
  `id` VARCHAR(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `pic` blob NOT NULL,
  `sid` VARCHAR(20) NOT NULL,
  `isordered` ENUM('Y', 'N') NOT NULL DEFAULT 'N',
  `isschool` ENUM('Y', 'N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`));
  CREATE TABLE `schoolsys`.`blacklist` (
  `id` VARCHAR(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `pic` blob NOT NULL,
  `contactid` VARCHAR(20) NOT NULL,
  `note` TEXT NOT NULL,
  PRIMARY KEY (`id`));
  CREATE TABLE `schoolsys`.`record` (
  `id`  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `identity` enum('student','worker','parent','blacklist') NOT NULL,
  `status` ENUM('allowed', 'notallowed', 'blacklist') NOT NULL,
  `pic` blob NOT NULL,
  `sid` VARCHAR(20) NULL,
  `tid` VARCHAR(20) NULL,
  `time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));
   alter table  `schoolsys`.`student` modify column spic MediumBlob ;
      alter table  `schoolsys`.`worker` modify column wpic MediumBlob ;
         alter table  `schoolsys`.`parent` modify column pic MediumBlob ;
            alter table  `schoolsys`.`blacklist` modify column pic MediumBlob ;
               alter table  `schoolsys`.`record` modify column pic MediumBlob ;