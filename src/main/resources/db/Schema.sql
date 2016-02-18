-- MYSQL
DROP TABLE IF EXISTS action;
CREATE TABLE action (
  id   BIGINT(20)   NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- POSTGRESQL
-- DROP TABLE IF EXISTS action;
-- DROP SEQUENCE IF EXISTS action_id_seq;
-- CREATE SEQUENCE action_id_seq;
-- CREATE TABLE action (
--   id        INTEGER DEFAULT NEXTVAL('action_id_seq'),
--   name     VARCHAR(32)
-- );