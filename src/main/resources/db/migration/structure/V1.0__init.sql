CREATE TABLE event (
    id UUID PRIMARY KEY NOT NULL,
    topic varchar(255),
    payload text,
    reason varchar(255),
    user_id varchar(255),
    _key varchar(255),
    note varchar(255)
);

CREATE TABLE reason (
    name varchar(255) PRIMARY KEY
);
