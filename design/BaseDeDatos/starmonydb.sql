-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema starmonydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema starmonydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `starmonydb` DEFAULT CHARACTER SET utf8 ;
USE `starmonydb` ;

-- -----------------------------------------------------
-- Table `starmonydb`.`estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`estado` (
  `id_estado` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_estado`),
  UNIQUE INDEX `id_estado_UNIQUE` (`id_estado` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`user` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` TIMESTAMP NOT NULL,
  `estado_id_estado` INT NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `id_user_UNIQUE` (`id_user` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_estado1_idx` (`estado_id_estado` ASC) VISIBLE,
  CONSTRAINT `fk_user_estado1`
    FOREIGN KEY (`estado_id_estado`)
    REFERENCES `starmonydb`.`estado` (`id_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`tag` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_tag`),
  UNIQUE INDEX `id_tag_UNIQUE` (`id_tag` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`chord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`chord` (
  `id_chord` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(10) NOT NULL,
  `code` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_chord`),
  UNIQUE INDEX `id_chord_UNIQUE` (`id_chord` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `intervals_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`genre` (
  `id_genre` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_genre`),
  UNIQUE INDEX `id_genre_UNIQUE` (`id_genre` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`state` (
  `id_state` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_state`),
  UNIQUE INDEX `id_state_UNIQUE` (`id_state` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`profile` (
  `id_profile` INT NOT NULL AUTO_INCREMENT,
  `genre_id_genre` INT NOT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `state_id_state` INT NOT NULL,
  PRIMARY KEY (`id_profile`),
  INDEX `fk_preference_genre1_idx` (`genre_id_genre` ASC) VISIBLE,
  INDEX `fk_profile_state1_idx` (`state_id_state` ASC) VISIBLE,
  UNIQUE INDEX `id_profile_UNIQUE` (`id_profile` ASC) VISIBLE,
  CONSTRAINT `fk_preference_genre1`
    FOREIGN KEY (`genre_id_genre`)
    REFERENCES `starmonydb`.`genre` (`id_genre`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_profile_state1`
    FOREIGN KEY (`state_id_state`)
    REFERENCES `starmonydb`.`state` (`id_state`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`interval`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`interval` (
  `id_interval` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(10) NOT NULL,
  `code` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_interval`),
  UNIQUE INDEX `id_interval_UNIQUE` (`id_interval` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`scale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`scale` (
  `id_scale` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_scale`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `id_scale_UNIQUE` (`id_scale` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`progression`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`progression` (
  `id_progression` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_progression`),
  UNIQUE INDEX `id_progression_UNIQUE` (`id_progression` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`progression_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`progression_has_tag` (
  `progression_id_progression` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`progression_id_progression`, `tag_id_tag`),
  INDEX `fk_progression_has_tag_tag1_idx` (`tag_id_tag` ASC) VISIBLE,
  INDEX `fk_progression_has_tag_progression1_idx` (`progression_id_progression` ASC) VISIBLE,
  CONSTRAINT `fk_progression_has_tag_progression1`
    FOREIGN KEY (`progression_id_progression`)
    REFERENCES `starmonydb`.`progression` (`id_progression`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_progression_has_tag_tag1`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `starmonydb`.`tag` (`id_tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`chord_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`chord_has_tag` (
  `chord_id_chord` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`chord_id_chord`, `tag_id_tag`),
  INDEX `fk_chord_has_tag_tag1_idx` (`tag_id_tag` ASC) VISIBLE,
  INDEX `fk_chord_has_tag_chord1_idx` (`chord_id_chord` ASC) VISIBLE,
  CONSTRAINT `fk_chord_has_tag_chord1`
    FOREIGN KEY (`chord_id_chord`)
    REFERENCES `starmonydb`.`chord` (`id_chord`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chord_has_tag_tag1`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `starmonydb`.`tag` (`id_tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`scale_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`scale_has_tag` (
  `scale_id_scale` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`scale_id_scale`, `tag_id_tag`),
  INDEX `fk_scale_has_tag_tag1_idx` (`tag_id_tag` ASC) VISIBLE,
  INDEX `fk_scale_has_tag_scale1_idx` (`scale_id_scale` ASC) VISIBLE,
  CONSTRAINT `fk_scale_has_tag_scale1`
    FOREIGN KEY (`scale_id_scale`)
    REFERENCES `starmonydb`.`scale` (`id_scale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scale_has_tag_tag1`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `starmonydb`.`tag` (`id_tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`interval_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`interval_has_tag` (
  `interval_id_interval` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`interval_id_interval`, `tag_id_tag`),
  INDEX `fk_interval_has_tag_tag1_idx` (`tag_id_tag` ASC) VISIBLE,
  INDEX `fk_interval_has_tag_interval1_idx` (`interval_id_interval` ASC) VISIBLE,
  CONSTRAINT `fk_interval_has_tag_interval1`
    FOREIGN KEY (`interval_id_interval`)
    REFERENCES `starmonydb`.`interval` (`id_interval`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interval_has_tag_tag1`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `starmonydb`.`tag` (`id_tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`user_has_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`user_has_profile` (
  `user_id_user` INT NOT NULL,
  `profile_id_profile` INT NOT NULL,
  PRIMARY KEY (`user_id_user`, `profile_id_profile`),
  INDEX `fk_user_has_profile_profile1_idx` (`profile_id_profile` ASC) VISIBLE,
  INDEX `fk_user_has_profile_user1_idx` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_profile_user1`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `starmonydb`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_profile_profile1`
    FOREIGN KEY (`profile_id_profile`)
    REFERENCES `starmonydb`.`profile` (`id_profile`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`user_has_interval`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`user_has_interval` (
  `user_id_user` INT NOT NULL,
  `interval_id_interval` INT NOT NULL,
  PRIMARY KEY (`user_id_user`, `interval_id_interval`),
  INDEX `fk_user_has_interval_interval1_idx` (`interval_id_interval` ASC) VISIBLE,
  INDEX `fk_user_has_interval_user1_idx` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_interval_user1`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `starmonydb`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_interval_interval1`
    FOREIGN KEY (`interval_id_interval`)
    REFERENCES `starmonydb`.`interval` (`id_interval`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`user_has_chord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`user_has_chord` (
  `user_id_user` INT NOT NULL,
  `chord_id_chord` INT NOT NULL,
  PRIMARY KEY (`user_id_user`, `chord_id_chord`),
  INDEX `fk_user_has_chord_chord1_idx` (`chord_id_chord` ASC) VISIBLE,
  INDEX `fk_user_has_chord_user1_idx` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_chord_user1`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `starmonydb`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_chord_chord1`
    FOREIGN KEY (`chord_id_chord`)
    REFERENCES `starmonydb`.`chord` (`id_chord`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`user_has_progression`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`user_has_progression` (
  `user_id_user` INT NOT NULL,
  `progression_id_progression` INT NOT NULL,
  PRIMARY KEY (`user_id_user`, `progression_id_progression`),
  INDEX `fk_user_has_progression_progression1_idx` (`progression_id_progression` ASC) VISIBLE,
  INDEX `fk_user_has_progression_user1_idx` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_progression_user1`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `starmonydb`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_progression_progression1`
    FOREIGN KEY (`progression_id_progression`)
    REFERENCES `starmonydb`.`progression` (`id_progression`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `starmonydb`.`user_has_scale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `starmonydb`.`user_has_scale` (
  `user_id_user` INT NOT NULL,
  `scale_id_scale` INT NOT NULL,
  PRIMARY KEY (`user_id_user`, `scale_id_scale`),
  INDEX `fk_user_has_scale_scale1_idx` (`scale_id_scale` ASC) VISIBLE,
  INDEX `fk_user_has_scale_user1_idx` (`user_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_scale_user1`
    FOREIGN KEY (`user_id_user`)
    REFERENCES `starmonydb`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_scale_scale1`
    FOREIGN KEY (`scale_id_scale`)
    REFERENCES `starmonydb`.`scale` (`id_scale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
