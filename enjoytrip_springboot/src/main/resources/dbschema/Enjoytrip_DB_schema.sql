-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb3 ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`do`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`do` (
  `do_idx` INT NOT NULL,
  `do_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`do_idx`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`sigungu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`sigungu` (
  `sigungu_idx` INT NOT NULL,
  `sigungu_do_idx` INT NOT NULL,
  `sigungu_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`sigungu_idx`, `sigungu_do_idx`),
  INDEX `sigungu_do_do_idx_fk_idx` (`sigungu_do_idx` ASC) VISIBLE,
  CONSTRAINT `sigungu_do_do_idx_fk`
    FOREIGN KEY (`sigungu_do_idx`)
    REFERENCES `enjoytrip`.`do` (`do_idx`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction` (
  `MyUnknownColumn` INT NULL DEFAULT NULL,
  `addr1` TEXT NULL DEFAULT NULL,
  `addr2` TEXT NULL DEFAULT NULL,
  `areacode` INT NULL DEFAULT NULL,
  `booktour` TEXT NULL DEFAULT NULL,
  `cat1` TEXT NULL DEFAULT NULL,
  `cat2` TEXT NULL DEFAULT NULL,
  `cat3` TEXT NULL DEFAULT NULL,
  `contentid` INT NOT NULL,
  `contenttypeid` INT NULL DEFAULT NULL,
  `createdtime` TEXT NULL DEFAULT NULL,
  `firstimage` TEXT NULL DEFAULT NULL,
  `firstimage2` TEXT NULL DEFAULT NULL,
  `mapx` DOUBLE NULL DEFAULT NULL,
  `mapy` DOUBLE NULL DEFAULT NULL,
  `mlevel` INT NULL DEFAULT NULL,
  `modifiedtime` TEXT NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sigungucode` INT NULL DEFAULT NULL,
  `tel` TEXT NULL DEFAULT NULL,
  `title` TEXT NULL DEFAULT NULL,
  `zipcode` TEXT NULL DEFAULT NULL,
  INDEX `attraction_sido_sido_idx_fx_idx` (`areacode` ASC) VISIBLE,
  INDEX `attraction_sigungu_sigungu_idx_idx` (`sigungucode` ASC) VISIBLE,
  PRIMARY KEY (`contentid`),
  CONSTRAINT `attraction_do_do_idx_fx`
    FOREIGN KEY (`areacode`)
    REFERENCES `enjoytrip`.`do` (`do_idx`),
  CONSTRAINT `attraction_sigungu_sigungu_idx`
    FOREIGN KEY (`sigungucode`)
    REFERENCES `enjoytrip`.`sigungu` (`sigungu_idx`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`members` (
  `user_id` VARCHAR(50) NOT NULL,
  `user_name` VARCHAR(50) NOT NULL,
  `user_isdel` TINYINT NOT NULL DEFAULT '0',
  `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_password` VARCHAR(100) NOT NULL,
  `email_id` VARCHAR(50) NULL DEFAULT NULL,
  `email_domain` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `board_img` VARCHAR(100) NULL DEFAULT NULL,
  `user_id` VARCHAR(50) NULL DEFAULT NULL,
  `content` VARCHAR(200) NULL DEFAULT NULL,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` INT NULL DEFAULT '0',
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`article_no`),
  INDEX `board_members_user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_to_members_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`members` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`memo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`memo` (
  `memo_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(50) NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `article_no` INT NULL DEFAULT NULL,
  `memo_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`memo_no`),
  INDEX `memo_to_board_article_no_fk_idx` (`article_no` ASC) VISIBLE,
  INDEX `memo_to_members_idx_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `memo_to_board_article_no_fk`
    FOREIGN KEY (`article_no`)
    REFERENCES `enjoytrip`.`board` (`article_no`),
  CONSTRAINT `memo_to_members_idx_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`members` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attractiondesc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attractiondesc` (
  `contentid` INT NOT NULL,
  `overview` TEXT NULL,
  PRIMARY KEY (`contentid`),
  CONSTRAINT `attraction_attractiondesc_contentid_fk`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction` (`contentid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plan` (
  `plan_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `user_id` VARCHAR(50) NULL,
  `plan_time` VARCHAR(45) NULL,
  PRIMARY KEY (`plan_id`),
  INDEX `plan_members_user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `plan_members_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`members` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plandetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plandetail` (
  `idx` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `plan_order` INT NOT NULL,
  `contentid` INT NOT NULL,
  PRIMARY KEY (`idx`),
  INDEX `plandetail_plan_plan_id_fk_idx` (`plan_id` ASC) VISIBLE,
  INDEX `plandetail_attraction_contentid_fk_idx` (`contentid` ASC) VISIBLE,
  CONSTRAINT `plandetail_plan_plan_id_fk`
    FOREIGN KEY (`plan_id`)
    REFERENCES `enjoytrip`.`plan` (`plan_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `plandetail_attraction_contentid_fk`
    FOREIGN KEY (`contentid`)
    REFERENCES `enjoytrip`.`attraction` (`contentid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
