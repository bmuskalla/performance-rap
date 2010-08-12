DROP TABLE IF EXISTS `tests` ;

CREATE  TABLE IF NOT EXISTS `tests` (
  `testId` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `testName` VARCHAR(255) NULL ,
  `testClass` VARCHAR(255) NULL ,
  PRIMARY KEY (`testId`) );
  
  
DROP TABLE IF EXISTS `testRuns` ;

CREATE  TABLE IF NOT EXISTS `testRuns` (
  `runId` INT NOT NULL AUTO_INCREMENT ,
  `testId` INT NOT NULL ,
  `date` DATETIME NULL ,
  PRIMARY KEY (`runId`) );
  

DROP TABLE IF EXISTS `iterations` ;

CREATE  TABLE IF NOT EXISTS `iterations` (
  `iterationId` INT NOT NULL AUTO_INCREMENT ,
  `testRunId` INT NOT NULL ,
  `result` MEDIUMTEXT NOT NULL ,
  PRIMARY KEY (`iterationId`, `testRunId`) );
