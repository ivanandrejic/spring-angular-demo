
INSERT INTO SECURE_USER (NAME, PASSWORD, ROLE) VALUES
--  ('admin', '$2a$04$V1iM.ArAssUEKeYZjlxSB.nqfedxNSKXClp6NpNqls2ToIPV9hMlC', 'ROLE_ADMIN'),
-- pass: demo
  ('admin', '$2a$04$V1iM.ArAssUEKeYZjlxSB.nqfedxNSKXClp6NpNqls2ToIPV9hMlC', 'ROLE_ADMIN'),
  ('create_user', '$2a$04$V1iM.ArAssUEKeYZjlxSB.nqfedxNSKXClp6NpNqls2ToIPV9hMlC', 'ROLE_CREATE_USER'),
  ('manager', '$2a$04$V1iM.ArAssUEKeYZjlxSB.nqfedxNSKXClp6NpNqls2ToIPV9hMlC', 'ROLE_USER_MANAGER'),
  ('user', '$2a$04$V1iM.ArAssUEKeYZjlxSB.nqfedxNSKXClp6NpNqls2ToIPV9hMlC', 'ROLE_USER');

  
INSERT INTO TIME_ZONE (ZONE_OFFSET, NAME, CITY, USER_ID) VALUES
  (1, 'Zone1', 'Rome', 4),
  (-12, 'Zone2', 'Singapure', 4),
  (14, 'Zone3', 'Paris', 4),
  (6, 'Zone2', 'Belgrade', 1),
  (14, 'Zone3', 'Rio', 1),
  (-8, 'Zone4', 'Mexico', 1);
  