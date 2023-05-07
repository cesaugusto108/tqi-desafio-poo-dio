CREATE DATABASE IF NOT EXISTS `bootcamp_tracker`;

USE `bootcamp_tracker`;

CREATE TABLE IF NOT EXISTS `bootcamp_tracker`.`bootcamp` (
  `data_encerramento` DATETIME(6) NULL DEFAULT NULL,
  `data_inicio` DATETIME(6) NULL DEFAULT NULL,
  `id` BIGINT NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  `detalhamento` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_4prkqpdk6jc0wsdb6m495cl9f` (`descricao` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bootcamp_tracker`.`atividade` (
  `carga_horaria` INT NULL DEFAULT NULL,
  `data` DATE NULL DEFAULT NULL,
  `id` BIGINT NOT NULL,
  `atividade_tipo` VARCHAR(31) NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_p4i8224gqw10q98850dmqpjc0` (`titulo` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bootcamp_tracker`.`pessoa` (
  `idade` INT NULL DEFAULT NULL,
  `nivel` INT NULL DEFAULT NULL,
  `id` BIGINT NOT NULL,
  `primeiro_nome` VARCHAR(30) NOT NULL,
  `sobrenome` VARCHAR(30) NOT NULL,
  `pessoa_tipo` VARCHAR(31) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_mc87q8fpvldpdyfo9o5633o5l` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bootcamp_tracker`.`bootcamp_atividades` (
  `atividade_id` BIGINT NOT NULL,
  `bootcamp_id` BIGINT NOT NULL,
  PRIMARY KEY (`atividade_id`, `bootcamp_id`),
  INDEX `FK1ipecbstqkrhu7tdvbt5g2v7r` (`bootcamp_id` ASC) VISIBLE,
  CONSTRAINT `FK1ipecbstqkrhu7tdvbt5g2v7r`
    FOREIGN KEY (`bootcamp_id`)
    REFERENCES `bootcamp_tracker`.`bootcamp` (`id`),
  CONSTRAINT `FKadr17r37pmh5o19qvq3t61l5o`
    FOREIGN KEY (`atividade_id`)
    REFERENCES `bootcamp_tracker`.`atividade` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `bootcamp_tracker`.`dev_bootcamp` (
  `bootcamp_id` BIGINT NOT NULL,
  `dev_id` BIGINT NOT NULL,
  PRIMARY KEY (`bootcamp_id`, `dev_id`),
  INDEX `FKmmb7tax0d8hif1il8il00okeb` (`dev_id` ASC) VISIBLE,
  CONSTRAINT `FK2wm9kpp4by4a5ey5bc7ev5kuk`
    FOREIGN KEY (`bootcamp_id`)
    REFERENCES `bootcamp_tracker`.`bootcamp` (`id`),
  CONSTRAINT `FKmmb7tax0d8hif1il8il00okeb`
    FOREIGN KEY (`dev_id`)
    REFERENCES `bootcamp_tracker`.`pessoa` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;