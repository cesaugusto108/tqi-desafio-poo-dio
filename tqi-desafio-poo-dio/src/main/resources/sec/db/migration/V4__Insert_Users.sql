INSERT INTO `user_tb` (`id`, `first_name`, `last_name`, `username`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`)
    VALUES (-1, '', '', 'test', '$2a$12$jws/Au.RE3ndOwNeykCfy.SpPa64cakXChiSlgR8seS0FAmicrmKq', 1, 1, 1, 1);

INSERT INTO `user_tb` (`id`, `first_name`, `last_name`, `username`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`)
    VALUES (1, 'Ana', 'Almeida', 'anaa', '$2a$12$jws/Au.RE3ndOwNeykCfy.SpPa64cakXChiSlgR8seS0FAmicrmKq', 1, 1, 1, 1);

INSERT INTO `user_tb` (`id`, `first_name`, `last_name`, `username`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`)
    VALUES (2, 'Carlos', 'Simões', 'csim', '$2a$12$jws/Au.RE3ndOwNeykCfy.SpPa64cakXChiSlgR8seS0FAmicrmKq', 1, 1, 1, 1);

INSERT INTO `user_role_tb` (`id`, `role`) VALUES (1, 'REGULAR');
INSERT INTO `user_role_tb` (`id`, `role`) VALUES (2, 'ADMIN');
INSERT INTO `user_role_tb` (`id`, `role`) VALUES (3, 'TEST');

INSERT INTO `users_roles_tb` (`user_id`, `role_id`) VALUES (-1, 3);
INSERT INTO `users_roles_tb` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `users_roles_tb` (`user_id`, `role_id`) VALUES (2, 1);
INSERT INTO `users_roles_tb` (`user_id`, `role_id`) VALUES (2, 2);
