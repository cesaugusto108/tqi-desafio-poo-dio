INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('instructor', 0xe8fd1a041c8545e08f358ee8520e1800, 32, 'florinda@email.com', 'Florinda', 'Santos', '', 'abcd', 'florsan', NULL);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('instructor', 0x4879ee9d27d34b4ba39c1360d70d5a04, 42, 'maria@email.com', 'Maria', 'Souza', '', 'abcd', 'marias', NULL);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('instructor', 0x4879ee9d27d34b4ba39c1360d70d5a00, 22, 'osvaldo@email.com', 'Osvaldo', 'Pires', '', 'abcd', 'osvaldop', NULL);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('developer', 0xd8b5e8ded9384daa9699b9cfcc599e37, 28, 'carlos@email.com', 'Carlos', 'Moura', 'Antônio', 'abcd', 'carlosan', 4);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('developer', 0x96f2c93edc1b4ce19aee989a2cd9f7ad, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 'abcd', 'josecc', 2);

INSERT INTO `person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)
    VALUES ('developer', 0x96f2c93edc1b4ce19aee989a2cd9f722, 49, 'fernando@email.com', 'Fernando', 'Alves', '', 'abcd', 'fernandoalv', 9);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES (0xd8b5e8ded9384daa9699b9cfcc599e37, -2);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES (0x96f2c93edc1b4ce19aee989a2cd9f7ad, -1);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES (0xd8b5e8ded9384daa9699b9cfcc599e37, -1);

INSERT INTO `developer_bootcamp` (`developer_id`, `bootcamp_id`) VALUES (0x96f2c93edc1b4ce19aee989a2cd9f7ad, -2);
