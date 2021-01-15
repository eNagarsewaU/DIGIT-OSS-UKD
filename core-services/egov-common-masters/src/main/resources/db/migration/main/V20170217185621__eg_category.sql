CREATE TABLE eg_category (
	id BIGINT NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	description CHARACTER VARYING(250),
	active BOOLEAN NOT NULL,
	tenantId CHARACTER VARYING(250) NOT NULL,

	CONSTRAINT pk_eg_category PRIMARY KEY (id),
	CONSTRAINT uk_eg_category_name UNIQUE (name)
);

CREATE SEQUENCE seq_eg_category
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;