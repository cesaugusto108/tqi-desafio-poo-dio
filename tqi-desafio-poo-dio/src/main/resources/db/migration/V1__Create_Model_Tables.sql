CREATE TABLE IF NOT EXISTS `seq_gen` (
  `next_val` bigint DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `sec_seq_gen` (
  `next_val` bigint DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `user_tb` (
  `id` int NOT NULL,
  `is_active` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lvx22t2upvjxxc86vf5daxc71` (`username`)
);

CREATE TABLE IF NOT EXISTS `user_role_tb` (
  `id` int NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_NORMAL','ROLE_TEST') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dt6okqixnqa9y4hnx7653g5y3` (`role`)
);

CREATE TABLE IF NOT EXISTS `user_roles_tb` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKjnxegwyiripkho8fntfsu1yxb` (`role_id`),
  CONSTRAINT `FKa4kh2ujo00f5k97wndxva3rts` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`id`),
  CONSTRAINT `FKjnxegwyiripkho8fntfsu1yxb` FOREIGN KEY (`role_id`) REFERENCES `user_role_tb` (`id`)
);

CREATE TABLE IF NOT EXISTS `bootcamp` (
  `id` int NOT NULL,
  `bootcamp_description` varchar(30) NOT NULL,
  `bootcamp_details` varchar(80) NOT NULL,
  `finish_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mklf6iud7jq63ul0edieiqu6s` (`bootcamp_description`)
);

CREATE TABLE IF NOT EXISTS `activity` (
  `activity_type` varchar(31) NOT NULL,
  `id` int NOT NULL,
  `activity_description` varchar(20) NOT NULL,
  `activity_details` varchar(80) NOT NULL,
  `mentoring_date` date DEFAULT NULL,
  `course_hours` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6mvt5w7fuwyekjk9fl3nm0fme` (`activity_description`)
);

CREATE TABLE IF NOT EXISTS `person` (
  `person_type` varchar(31) NOT NULL,
  `id` int NOT NULL,
  `person_age` int DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `middle_name` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(30) NOT NULL,
  `developer_level` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `bootcamp_activity` (
  `bootcamp_id` int NOT NULL,
  `activity_id` int NOT NULL,
  PRIMARY KEY (`bootcamp_id`,`activity_id`),
  KEY `FKa1obwkopl6kn5575x9dldr05` (`activity_id`),
  CONSTRAINT `FK9w9htf3ihuqhedqcbeif6vpd8` FOREIGN KEY (`bootcamp_id`) REFERENCES `bootcamp` (`id`),
  CONSTRAINT `FKa1obwkopl6kn5575x9dldr05` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
);

CREATE TABLE IF NOT EXISTS `developer_bootcamp` (
  `developer_id` int NOT NULL,
  `bootcamp_id` int NOT NULL,
  PRIMARY KEY (`developer_id`,`bootcamp_id`),
  KEY `FKdgrkkb2m1v970t9eprqx5ba88` (`bootcamp_id`),
  CONSTRAINT `FKdgrkkb2m1v970t9eprqx5ba88` FOREIGN KEY (`bootcamp_id`) REFERENCES `bootcamp` (`id`),
  CONSTRAINT `FKm11ly2r9kgmh0c2edw5nnprua` FOREIGN KEY (`developer_id`) REFERENCES `person` (`id`)
);