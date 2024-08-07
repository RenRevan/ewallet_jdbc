-- MySQL Script generated by MySQL Workbench
-- Mon Jul 15 23:05:33 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ewalletdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ewalletdb` ;

-- -----------------------------------------------------
-- Schema ewalletdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ewalletdb` DEFAULT CHARACTER SET utf8 ;
USE `ewalletdb` ;

-- -----------------------------------------------------
-- Table `ewalletdb`.`customer_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`customer_role` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`customer_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`customer` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mobile_number` VARCHAR(12) NOT NULL,
  `email` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `customer_role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_customer_customer_role1_idx` (`customer_role_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_customer_role1`
    FOREIGN KEY (`customer_role_id`)
    REFERENCES `ewalletdb`.`customer_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`wallet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`wallet` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`wallet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_wallet_customer1_idx` (`customer_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_wallet_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ewalletdb`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`bank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`bank` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`bank` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `mfo` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`currency` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`currency` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`account` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `iban` VARCHAR(45) NOT NULL,
  `balance` INT NULL,
  `wallet_id` INT NOT NULL,
  `bank_id` INT NOT NULL,
  `currency_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_account_wallet1_idx` (`wallet_id` ASC) VISIBLE,
  INDEX `fk_account_bank1_idx` (`bank_id` ASC) VISIBLE,
  INDEX `fk_account_currency1_idx` (`currency_id` ASC) VISIBLE,
  CONSTRAINT `fk_account_wallet1`
    FOREIGN KEY (`wallet_id`)
    REFERENCES `ewalletdb`.`wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_bank1`
    FOREIGN KEY (`bank_id`)
    REFERENCES `ewalletdb`.`bank` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_currency1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `ewalletdb`.`currency` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`operation_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`operation_code` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`operation_code` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`transaction` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` INT NOT NULL,
  `date` DATETIME NOT NULL,
  `description` VARCHAR(225) NULL,
  `iban_from` VARCHAR(45) NOT NULL,
  `iban_to` VARCHAR(45) NOT NULL,
  `account_id` INT NOT NULL,
  `operation_code_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transaction_account1_idx` (`account_id` ASC) VISIBLE,
  INDEX `fk_transaction_operation_code1_idx` (`operation_code_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `ewalletdb`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_operation_code1`
    FOREIGN KEY (`operation_code_id`)
    REFERENCES `ewalletdb`.`operation_code` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`beneficiary`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`beneficiary` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`beneficiary` (
  `id` INT NOT NULL,
  `iban` VARCHAR(45) NULL,
  `bank_name` VARCHAR(45) NULL,
  `mfo` INT NULL,
  `full_name` VARCHAR(45) NULL,
  `wallet_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_beneficiary_wallet1_idx` (`wallet_id` ASC) VISIBLE,
  CONSTRAINT `fk_beneficiary_wallet1`
    FOREIGN KEY (`wallet_id`)
    REFERENCES `ewalletdb`.`wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ewalletdb`.`customer_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ewalletdb`.`customer_session` ;

CREATE TABLE IF NOT EXISTS `ewalletdb`.`customer_session` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `session_datetime` DATETIME NULL,
  `browser` VARCHAR(45) NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_customer_session_customer1_idx` (`customer_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_session_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ewalletdb`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
