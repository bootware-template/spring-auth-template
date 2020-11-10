CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE user_info (
    id VARCHAR(40) PRIMARY KEY DEFAULT uuid_generate_v4 (),
    ext_auth_id VARCHAR(40),
	email VARCHAR(100) UNIQUE,
	enabled BOOLEAN,
	password VARCHAR(200),
	provider VARCHAR(255),
	username VARCHAR(50) UNIQUE,
    ${commonColumns}
);

CREATE TABLE authority (
    id VARCHAR(40) PRIMARY KEY DEFAULT uuid_generate_v4 (),
    ext_auth_id VARCHAR(40),
	name VARCHAR(100) UNIQUE,
	${commonColumns}
);

CREATE TABLE user_authority (
	user_id VARCHAR(40) NOT NULL,
	authority_id VARCHAR(40) NOT NULL,
	${commonColumns},
	CONSTRAINT user_authority_pk PRIMARY KEY (user_id, authority_id),
	CONSTRAINT user_authority_fk1 FOREIGN KEY (user_id) REFERENCES user_info (id),
	CONSTRAINT user_authority_fk2 FOREIGN KEY (authority_id) REFERENCES authority (id)
);
