
INSERT INTO `bootcamp_tracker_dev`.`sec_seq_gen` (`next_val`)
    VALUES (1000);

INSERT INTO `bootcamp_tracker_dev`.`seq_gen` (`next_val`)
    VALUES (1);

INSERT INTO `bootcamp_tracker_dev`.`bootcamp`(`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)
    VALUES (-2, 'Linux Experience', 'Aperfeiçoamento Linux', NULL, NULL);

INSERT INTO `bootcamp_tracker_dev`.`bootcamp`(`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)
    VALUES (-1, 'TQI Kotlin Backend', 'Java e Kotlin backend', NULL, NULL);

INSERT INTO `bootcamp_tracker_dev`.`activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)
    VALUES ('course', -2, 'Sintaxe Java', 'Aprendendo a sintaxe Java', NULL, 300);

INSERT INTO `bootcamp_tracker_dev`.`activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)
    VALUES ('mentoring', -1, 'Orientação a objetos', 'Orientação a objetos com Kotlin', NULL, NULL);

INSERT INTO `bootcamp_tracker_dev`.`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('instructor', -2, 32, 'maria@email.com', 'Maria', 'Souza', '', 'abcd', 'marias', NULL);

INSERT INTO `bootcamp_tracker_dev`.`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('developer', -1, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 'abcd', 'josecc', 2);

INSERT INTO `bootcamp_tracker_dev`.`bootcamp_activity` (`bootcamp_id`, `activity_id`)
    VALUES (-1, -1);

INSERT INTO `bootcamp_tracker_dev`.`bootcamp_activity` (`bootcamp_id`, `activity_id`)
    VALUES (-1, -2);

INSERT INTO `bootcamp_tracker_dev`.`developer_bootcamp` (`developer_id`, `bootcamp_id`)
    VALUES ( -1, -1);