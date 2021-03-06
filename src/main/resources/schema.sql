CREATE TABLE IF NOT EXISTS `client` (
    `id` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `short_name` VARCHAR(255) NOT NULL,
    `address` TEXT NOT NULL,
    `legal_type` INT UNSIGNED NOT NULL,
    `is_deleted` TINYINT(1) UNSIGNED DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `bank` (
    `id` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `bic` VARCHAR(255) NOT NULL,
    `is_deleted` TINYINT(1) UNSIGNED DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_bic` (`bic`)
);

CREATE TABLE IF NOT EXISTS `deposit` (
    `id` INT UNSIGNED AUTO_INCREMENT NOT NULL,
    `client_id` INT UNSIGNED NOT NULL,
    `bank_id` INT UNSIGNED NOT NULL,
    `date_opened` TIMESTAMP NOT NULL,
    `percent` FLOAT NOT NULL,
    `months` INT UNSIGNED NOT NULL,
    `is_deleted` TINYINT(1) UNSIGNED DEFAULT 0,
    PRIMARY KEY (`id`)
);