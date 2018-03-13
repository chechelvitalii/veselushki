CREATE TABLE IF NOT EXISTS facebook_post (
	topic_link VARCHAR(255) NOT NULL,
	label VARCHAR(255) NULL DEFAULT NULL,
	status VARCHAR(4) NOT NULL,
	created_timestamp DATETIME(6) NOT NULL,
	sent_timestamp DATETIME(6) NULL DEFAULT NULL,
	PRIMARY KEY (topic_link)
);