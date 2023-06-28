CREATE TABLE IF NOT EXISTS `person` (
  `person_type` varchar(31) NOT NULL,
  `person_id` int NOT NULL AUTO_INCREMENT,
  `person_age` int DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `middle_name` varchar(20) NOT NULL,
  `developer_level` int DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `activity` (
  `activity_type` varchar(31) NOT NULL,
  `person_id` int NOT NULL AUTO_INCREMENT,
  `activity_description` varchar(20) NOT NULL,
  `activity_details` varchar(80) NOT NULL,
  `course_hours` int NOT NULL,
  `mentoring_date` date NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `UK_6mvt5w7fuwyekjk9fl3nm0fme` (`activity_description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bootcamp` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `bootcamp_description` varchar(30) NOT NULL,
  `bootcamp_details` varchar(80) NOT NULL,
  `finish_date` datetime(6) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `UK_mklf6iud7jq63ul0edieiqu6s` (`bootcamp_description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bootcamp_activity` (
  `bootcamp_id` int NOT NULL,
  `activity_id` int NOT NULL,
  PRIMARY KEY (`bootcamp_id`,`activity_id`),
  KEY `FKa1obwkopl6kn5575x9dldr05` (`activity_id`),
  CONSTRAINT `FK9w9htf3ihuqhedqcbeif6vpd8` FOREIGN KEY (`bootcamp_id`) REFERENCES `bootcamp` (`person_id`),
  CONSTRAINT `FKa1obwkopl6kn5575x9dldr05` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `developer_bootcamp` (
  `developer_id` int NOT NULL,
  `bootcamp_id` int NOT NULL,
  PRIMARY KEY (`developer_id`,`bootcamp_id`),
  KEY `FKdgrkkb2m1v970t9eprqx5ba88` (`bootcamp_id`),
  CONSTRAINT `FKdgrkkb2m1v970t9eprqx5ba88` FOREIGN KEY (`bootcamp_id`) REFERENCES `bootcamp` (`person_id`),
  CONSTRAINT `FKm11ly2r9kgmh0c2edw5nnprua` FOREIGN KEY (`developer_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;