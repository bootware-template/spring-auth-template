-- Admin
INSERT INTO m_user (id, user_name, password, mail_address)
VALUES ('system', 'Admin', '$2a$10$zgPrRttBhV9k8kI5AOv2P.dVXX6HFdynM.7lXp4RJkLFaywzAxIOy', 'admin@bootware.jp');

-- User
INSERT INTO m_user (id, user_name, password, unlocked, mail_address)
VALUES ('user1', 'User1', '$2a$10$zgPrRttBhV9k8kI5AOv2P.dVXX6HFdynM.7lXp4RJkLFaywzAxIOy', true, 'user1@bootware.jp'),
       ('user2', 'User2', '$2a$10$zgPrRttBhV9k8kI5AOv2P.dVXX6HFdynM.7lXp4RJkLFaywzAxIOy', true, 'user2@bootware.jp'),
       ('user3', 'User3', '$2a$10$zgPrRttBhV9k8kI5AOv2P.dVXX6HFdynM.7lXp4RJkLFaywzAxIOy', false, 'user3@bootware.jp');

-- Role
INSERT INTO m_role (id, role_name)
VALUES ('admin', 'ROLE_ADMIN'),
       ('general', 'ROLE_GENERAL');

-- UserRole
INSERT INTO t_user_role (user_id, role_id)
VALUES ('system', 'admin'),
       ('system', 'general'),
       ('user1', 'general'),
       ('user2', 'general'),
       ('user3', 'general');
