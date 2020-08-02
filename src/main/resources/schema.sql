DROP DATABASE IF EXISTS cnode;
CREATE DATABASE cnode CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cnode;

CREATE TABLE `cnode`.`Topic` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `content` text NULL,
    `title` VARCHAR(45) NULL,
    `deleted` BIT(1) NULL,
    `createdTime` BIGINT NULL,
    `updatedTime` BIGINT NULL,
    `userId` INT NULL,
    `viewCount` INT NULL,
    `replyCount` INT NULL,
    `tab` VARCHAR(45) NULL,
    PRIMARY KEY (`id`));

CREATE TABLE `cnode`.`Comment` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `content` text NULL,
    `deleted` BIT(1) NULL,
    `createdTime` BIGINT NULL,
    `updatedTime` BIGINT NULL,
    `userId` INT NULL,
    `topicId` INT NULL,
    `floor` INT NULL,
    PRIMARY KEY (`id`));

