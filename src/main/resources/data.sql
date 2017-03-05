
INSERT INTO SECURE_USER (NAME, PASSWORD, ROLE) VALUES
--  ('admin', '$2a$10$HXI22ZmiVyz.NbWgSzye.eXvSWPPrIEhiydH6G3zjdrPA3a9rfkzm', 'ROLE_ADMIN'),
-- pass: toptal
  ('admin', '$2a$10$HXI22ZmiVyz.NbWgSzye.eXvSWPPrIEhiydH6G3zjdrPA3a9rfkzm', 'ROLE_ADMIN'),
  ('create_user', '$2a$04$Y9N.iT38uAI8xcxz17pjdekBg05ShBhi2n170q5bwVDIHjsUcNMfK', 'ROLE_CREATE_USER'),
  ('manager', '$2a$10$HXI22ZmiVyz.NbWgSzye.eXvSWPPrIEhiydH6G3zjdrPA3a9rfkzm', 'ROLE_USER_MANAGER'),
  ('user', '$2a$10$HXI22ZmiVyz.NbWgSzye.eXvSWPPrIEhiydH6G3zjdrPA3a9rfkzm', 'ROLE_USER');

  
INSERT INTO TIME_ZONE (TIME_ZONE, ZONE_OFFSET, NAME, CITY, USER_ID) VALUES
  ('2017-01-12 12:00:00', 1, 'Zone1', 'Rome', 4),
  ('2017-01-13 12:00:00', -12, 'Zone2', 'Singapure', 4),
  ('2017-01-14 12:00:00', 14, 'Zone3', 'Paris', 4),
  ('2017-01-12 12:00:00', 6, 'Zone2', 'Belgrade', 1),
  ('2017-01-13 12:00:00', 14, 'Zone3', 'Rio', 1),
  ('2017-01-14 12:00:00', -8, 'Zone4', 'Mexico', 1);
  