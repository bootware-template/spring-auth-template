CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE m_user (
    user_id VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
    ext_auth_id VARCHAR(40),
    user_name VARCHAR(50),
    password VARCHAR(200),
    password_updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    login_failed_times INT DEFAULT 0,
    unlocked BOOLEAN DEFAULT true,
	mail_address VARCHAR(100) UNIQUE,
	enabled BOOLEAN DEFAULT true,
    ${commonColumns},
    CONSTRAINT m_user_pk PRIMARY KEY (user_id)
);

CREATE TABLE m_role (
    role_id VARCHAR(40) NOT NULL DEFAULT uuid_generate_v4(),
    role_name VARCHAR(100),
	${commonColumns},
	CONSTRAINT m_role_pk PRIMARY KEY (role_id)
);

CREATE SEQUENCE seq_t_user_role_id;
CREATE TABLE t_user_role (
	id INT NOT NULL DEFAULT nextval('seq_t_user_role_id'),
	user_id VARCHAR(40) NOT NULL,
	role_id VARCHAR(40) NOT NULL,
	${commonColumns},
	CONSTRAINT t_user_role_pk PRIMARY KEY (id),
	CONSTRAINT t_user_role_fk1 FOREIGN KEY (user_id) REFERENCES m_user (user_id),
	CONSTRAINT t_user_role_fk2 FOREIGN KEY (role_id) REFERENCES m_role (role_id)
);
