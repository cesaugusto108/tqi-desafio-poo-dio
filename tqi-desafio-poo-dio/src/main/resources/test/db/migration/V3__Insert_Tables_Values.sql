INSERT INTO `bootcamp` (`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)
    VALUES (-3, 'AWS Experience', 'Cloud Computing', NULL, NULL);

INSERT INTO `bootcamp` (`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)
    VALUES (-2, 'Linux Experience', 'Aperfeiçoamento Linux', NULL, NULL);

INSERT INTO `bootcamp` (`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)
    VALUES (-1, 'TQI Kotlin Backend', 'Java e Kotlin backend', NULL, NULL);

INSERT INTO `activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)
    VALUES ('course', -4, 'Sintaxe Kotlin', 'Aprendendo a sintaxe Kotlin', NULL, 300);

INSERT INTO `activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)
    VALUES ('course', -3, 'Sintaxe Java', 'Aprendendo a sintaxe Java', NULL, 300);

INSERT INTO `activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)
    VALUES ('mentoring', -2, 'Java - POO', 'Orientação a objetos com Java', NULL, NULL);

INSERT INTO `activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)
    VALUES ('mentoring', -1, 'Kotlin - POO', 'Orientação a objetos com Kotlin', NULL, NULL);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('instructor', -4, 32, 'florinda@email.com', 'Florinda', 'Santos', '', 'abcd', 'florsan', NULL);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('instructor', -3, 42, 'maria@email.com', 'Maria', 'Souza', '', 'abcd', 'marias', NULL);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('developer', -2, 28, 'carlos@email.com', 'Carlos', 'Moura', 'Antônio', 'abcd', 'carlosan', 4);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('developer', -1, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 'abcd', 'josecc', 2);

INSERT INTO `bootcamp_activity` (`bootcamp_id`, `activity_id`) VALUES (-2, -4);

INSERT INTO `bootcamp_activity` (`bootcamp_id`, `activity_id`) VALUES (-3, -3);

INSERT INTO `bootcamp_activity` (`bootcamp_id`, `activity_id`) VALUES (-1, -1);

INSERT INTO `bootcamp_activity` (`bootcamp_id`, `activity_id`) VALUES (-1, -2);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES ( -2, -2);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES ( -1, -1);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES ( -2, -1);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES ( -1, -2);
