set database sql syntax PGS true;

CREATE TABLE t_exposed(
 pk_exposed_id Serial NOT NULL,
 key Text NOT NULL,
 received_at Timestamp with time zone DEFAULT now() NOT NULL,
 onset Date NOT NULL,
 app_source Character varying(50) NOT NULL
);

-- Add keys for table t_exposed

ALTER TABLE t_exposed ADD CONSTRAINT PK_t_exposed PRIMARY KEY (pk_exposed_id);

ALTER TABLE t_exposed ADD CONSTRAINT key UNIQUE (key);


CREATE TABLE t_redeem_code(
 pk_redeem_code VARCHAR(10) NOT NULL,
 is_used Boolean Default False NOT NULL
);

-- Add keys for table t_redeem_code

ALTER TABLE t_redeem_code ADD CONSTRAINT PK_t_redeem_code PRIMARY KEY (pk_redeem_code);

-- some test redeem codes
INSERT INTO t_redeem_code values ('TESTCODE1', False);
INSERT INTO t_redeem_code values ('TESTCODE2', False);