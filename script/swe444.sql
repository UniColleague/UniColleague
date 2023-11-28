use `swe444`;

DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `user`; 

CREATE TABLE `user` (
  `user_id` varchar(50) NOT NULL,
  `pw` char(68) NOT NULL,
  `active` tinyint NOT NULL,
  `email` varchar(50) NOT NULL,
  `image` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user`
VALUES
('Yousef','{noop}123',1,'Yousef@gmail.com',null);

CREATE TABLE `task` (
  `user_id` varchar(50) NOT NULL,
  `task_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `state` boolean DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `edate` varchar(50) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FK_USER1_idx` (`user_id`),
  CONSTRAINT `task1_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `task`
VALUES
('Yousef',1,'First Task','First Task des',false,'2023/10/16','2023/10/18','Projects'),
('Yousef',2,'Second Task','second Task des',false,'2023/10/16','2023/10/19','Exams and Assessments');

CREATE TABLE `roles` (
   `user_id` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `authorities5_idx_1` (`user_id`,`role`),
  CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `roles`
VALUES
('Yousef','ROLE_USER');