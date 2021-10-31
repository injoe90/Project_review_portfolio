DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS menu_cate;

CREATE TABLE menu_cate(
	cate_id INT(5),
	cate_name NVARCHAR(20)	
);

CREATE TABLE menu(
	menu_id INT(5) NOT NULL AUTO_INCREMENT,
	menu_name NVARCHAR(20),
	cate_id INT(5),
	price INT(10),
	menu_state INT(2),
	menu_title NVARCHAR(20)
);




#데이터 입력



INSERT INTO employee VALUES (1111,"cafe_manager");
INSERT INTO employee VALUES (2222,"cafe_staff");
INSERT INTO employee VALUES (3333,"cafe_staff");
INSERT INTO employee VALUES (4444,"cafe_staff");

ALTER TABLE menu_cate ADD PRIMARY KEY(cate_id);
ALTER TABLE menu ADD PRIMARY KEY(menu_id, menu_name);
ALTER TABLE menu AUTO_INCREMENT = 1;
ALTER TABLE menu modify menu_name NOT NUll;

#--------FOREIGN KEY
ALTER TABLE menu ADD CONSTRAINT cate_id_fk FOREIGN KEY(cate_id) REFERENCES menu_cate(cate_id);