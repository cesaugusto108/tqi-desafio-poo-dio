DROP TABLE `developer_bootcamp`;
DROP TABLE `person`;

CREATE TABLE `person` (
  `person_type` varchar(31) NOT NULL,
  `id` binary(16) NOT NULL,
  `person_age` int DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `middle_name` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(30) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `developer_level` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
);

CREATE TABLE IF NOT EXISTS `developer_bootcamp` (
  `developer_id` binary(16) NOT NULL,
  `bootcamp_id` int NOT NULL,
  PRIMARY KEY (`developer_id`,`bootcamp_id`),
  KEY `FKdgrkkb2m1v970t9eprqx5ba88` (`bootcamp_id`),
  CONSTRAINT `FKdgrkkb2m1v970t9eprqx5ba88` FOREIGN KEY (`bootcamp_id`) REFERENCES `bootcamp` (`id`),
  CONSTRAINT `FKm11ly2r9kgmh0c2edw5nnprua` FOREIGN KEY (`developer_id`) REFERENCES `person` (`id`)
);