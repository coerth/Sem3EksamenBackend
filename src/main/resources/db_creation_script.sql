-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sem3eksamen
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `sem3eksamen` ;

-- -----------------------------------------------------
-- Schema sem3eksamen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sem3eksamen` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `sem3eksamen` ;

-- -----------------------------------------------------
-- Table `sem3eksamen`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`role` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sem3eksamen`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`user` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(25) NOT NULL,
  `user_pass` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sem3eksamen`.`user_has_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`user_has_role` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`user_has_role` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_has_role_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_user_has_role_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `sem3eksamen`.`role` (`id`),
  CONSTRAINT `fk_user_has_role_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sem3eksamen`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sem3eksamen`.`owner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`owner` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`owner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sem3eksamen`.`dog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`dog` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`dog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(45) NULL,
  `gender` ENUM('Male', 'Female') NOT NULL,
  `birthdate` DATETIME NOT NULL,
  `owner_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Dog_owner1_idx` (`owner_id` ASC) VISIBLE,
  CONSTRAINT `fk_Dog_owner1`
    FOREIGN KEY (`owner_id`)
    REFERENCES `sem3eksamen`.`owner` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sem3eksamen`.`walker`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`walker` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`walker` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sem3eksamen`.`walker_has_dog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sem3eksamen`.`walker_has_dog` ;

CREATE TABLE IF NOT EXISTS `sem3eksamen`.`walker_has_dog` (
  `Walker_id` INT NOT NULL,
  `Dog_id` INT NOT NULL,
  PRIMARY KEY (`Walker_id`, `Dog_id`),
  INDEX `fk_Walker_has_Dog_Dog1_idx` (`Dog_id` ASC) VISIBLE,
  INDEX `fk_Walker_has_Dog_Walker1_idx` (`Walker_id` ASC) VISIBLE,
  CONSTRAINT `fk_Walker_has_Dog_Walker1`
    FOREIGN KEY (`Walker_id`)
    REFERENCES `sem3eksamen`.`walker` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Walker_has_Dog_Dog1`
    FOREIGN KEY (`Dog_id`)
    REFERENCES `sem3eksamen`.`dog` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
