DROP DATABASE IF EXISTS cal;
CREATE DATABASE cal;

CREATE TABLE `cal`.`users`(
	userID int(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	email varchar(40) NOT NULL,
    -- primary key
    fname varchar(60) NOT NULL,
    lname varchar(60) NOT NULL
);

CREATE TABLE `cal`.`groups`(
	groupID int(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	userID int(4) NOT NULL,
	groupName varchar(60) NOT NULL,  

    FOREIGN KEY (userID) REFERENCES users(userID)
);  


CREATE TABLE `cal`.`schedule` (
	scheduleID int(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	userID int(4) NOT NULL,
	yearID int(4),
	monthID int(2),
	dateID int(2),
	startTime varchar(16),
	endTime varchar(16),
	FOREIGN KEY (userID) REFERENCES users(userID)
);


INSERT INTO cal.users (email, fname, lname)
VALUES ('lindsayjhuang@gmail.com', 'lindsay', 'huang');

INSERT INTO cal.users (email, fname, lname)
VALUES ('tommytrojan@usc.edu', 'tommy', 'trojan');

INSERT INTO cal.users (email, fname, lname)
VALUES ('huan981@usc.edu', 'linds', 'h');



INSERT INTO cal.groups (userID, groupName)
VALUES (1, '201 Group Project');

INSERT INTO cal.groups (userID, groupName)
VALUES (2, '201 Group Project');

INSERT INTO cal.groups (userID, groupName)
VALUES (3, 'Code the Change');

INSERT INTO cal.groups (userID, groupName)
VALUES (3, '201 Group Project');

INSERT INTO cal.groups (userID, groupName)
VALUES (2, 'Code the Change');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 4, 7, '14:00:00-05:00', '16:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 4, 12, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (2, 2020, 3, 21, '11:00:00-05:00', '13:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (2, 2020, 3, 22, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (2, 2020, 3, 25, '14:00:00-05:00', '16:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (2, 2020, 2, 29, '11:00:00-05:00', '13:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 4, 3, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 4, 4, '14:00:00-05:00', '16:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 3, 21, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 3, 22, '14:00:00-05:00', '17:00:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 3, 25, '14:00:00-05:00', '17:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 4, 1, '11:00:00-05:00', '13:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 4, 3, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 4, 4, '15:00:00-05:00', '16:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 4, 6, '12:00:00-05:00', '17:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 4, 9, '11:00:00-05:00', '13:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 4, 11, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 4, 15, '14:00:00-05:00', '16:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (3, 2020, 3, 21, '12:00:00-05:00', '14:30:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 3, 22, '14:00:00-05:00', '17:00:00-05:00');


INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 3, 25, '14:00:00-05:00', '17:10:00-05:00');

INSERT INTO cal.schedule (userID, yearId, monthId, dateId, startTime, endTime)
VALUES (1, 2020, 4, 1, '11:00:00-05:00', '13:30:00-05:00');