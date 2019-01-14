create table event(version int, time bigint PRIMARY KEY, message text);

INSERT INTO event(version, time, message) values(1,1234123412,'foula');
INSERT INTO event(version, time, message) values(1,1234123413,'koula');
INSERT INTO event(version, time, message) values(1,1234123414,'soula');

