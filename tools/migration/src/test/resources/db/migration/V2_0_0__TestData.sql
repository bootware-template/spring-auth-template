INSERT INTO authority(id, name) VALUES ('a349521s-8315-61d2-m1ds-731hls7ss89dk', 'ROLE_ADMIN');
INSERT INTO authority(id, name) VALUES ('b349521s-8315-61d2-m1ds-731hls7ss89dk', 'ROLE_USER');

-- Admin Role
INSERT INTO user_info(id, email, enabled, password, provider, username)
VALUES ('d349521s-8315-61d2-m1ds-731hls7ss89dk', 'admin@bootware.jp', 'true', '$2a$10$zgPrRttBhV9k8kI5AOv2P.dVXX6HFdynM.7lXp4RJkLFaywzAxIOy', 'LOCAL', 'admin');
INSERT INTO user_authority(user_id, authority_id) VALUES ('d349521s-8315-61d2-m1ds-731hls7ss89dk', 'a349521s-8315-61d2-m1ds-731hls7ss89dk');
INSERT INTO user_authority(user_id, authority_id) VALUES ('d349521s-8315-61d2-m1ds-731hls7ss89dk', 'b349521s-8315-61d2-m1ds-731hls7ss89dk');

-- User Role
INSERT INTO user_info(id, email, enabled, password, provider, username)
VALUES ('e349521s-8315-61d2-m1ds-731hls7ss89dk', 'user@bootware.jp', 'true', '$2a$10$zgPrRttBhV9k8kI5AOv2P.dVXX6HFdynM.7lXp4RJkLFaywzAxIOy', 'LOCAL', 'user');
INSERT INTO user_authority(user_id, authority_id) VALUES ('e349521s-8315-61d2-m1ds-731hls7ss89dk', 'b349521s-8315-61d2-m1ds-731hls7ss89dk');
