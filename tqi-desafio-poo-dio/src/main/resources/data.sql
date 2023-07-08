delete from developer_bootcamp;

delete from bootcamp_activity;

delete from activity;

delete from bootcamp;

delete from person;

insert into bootcamp values (-2, 'Linux Experience', 'Aperfeiçoamento Linux', NULL, NULL);
insert into bootcamp values (-1, 'TQI Kotlin Backend', 'Java e Kotlin backend', NULL, NULL);

insert into activity values ('course', -2, 'Sintaxe Java', 'Aprendendo a sintaxe Java', NULL, 300);
insert into activity values ('mentoring', -1, 'Orientação a objetos', 'Orientação a objetos com Kotlin', NULL, NULL);

insert into person values ('instructor', -2, 32, 'maria@email.com', 'Maria', 'Souza', '', 'abcd', 'marias', NULL);
insert into person values ('developer', -1, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 'abcd', 'josecc', 2);

insert into bootcamp_activity values (-1, -1);
insert into bootcamp_activity values (-1, -2);

insert into developer_bootcamp values (-1, -1);
