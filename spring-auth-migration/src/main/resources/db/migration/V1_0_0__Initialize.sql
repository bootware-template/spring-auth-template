CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE m_user(
                       id                    VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
                       ext_auth_id           VARCHAR(40),
                       user_name             VARCHAR(50),
                       password              VARCHAR(200),
                       password_updated_date TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
                       login_failed_times    INT                  DEFAULT 0,
                       unlocked              BOOLEAN              DEFAULT true,
                       mail_address          VARCHAR(100) UNIQUE,
                       enabled               BOOLEAN              DEFAULT true,
                       ${commonColumns},
                       CONSTRAINT m_user_pk PRIMARY KEY (id)
);

CREATE TABLE m_role
(
    id        VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
    role_name VARCHAR(100),
    ${commonColumns},
    CONSTRAINT m_role_pk PRIMARY KEY (id)
);

CREATE TABLE t_user_role
(
    id      VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
    user_id VARCHAR(40) NOT NULL,
    role_id VARCHAR(40) NOT NULL,
    ${commonColumns},
    CONSTRAINT t_user_role_pk PRIMARY KEY (id),
    CONSTRAINT t_user_role_fk1 FOREIGN KEY (user_id) REFERENCES m_user (id),
    CONSTRAINT t_user_role_fk2 FOREIGN KEY (role_id) REFERENCES m_role (id)
);

CREATE TABLE m_action_authority
(
    id          VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
    action_name VARCHAR(100),
    ${commonColumns},
    CONSTRAINT m_action_authority_pk PRIMARY KEY (id)
);

CREATE TABLE t_user_action_authority
(
    id                  VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
    user_id             VARCHAR(40) NOT NULL,
    action_authority_id VARCHAR(40) NOT NULL,
    ${commonColumns},
    CONSTRAINT t_user_action_authority_pk PRIMARY KEY (id),
    CONSTRAINT t_user_action_authority_fk1 FOREIGN KEY (user_id) REFERENCES m_user (id),
    CONSTRAINT t_user_action_authority_fk2 FOREIGN KEY (action_authority_id) REFERENCES m_action_authority (id)
);